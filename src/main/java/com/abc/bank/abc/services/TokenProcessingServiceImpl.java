package com.abc.bank.abc.services;

import com.abc.bank.abc.enums.CustomerType;
import com.abc.bank.abc.exceptions.IllegalInputException;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.utilities.DateUtility;
import com.abc.bank.abc.utilities.TokenNumberGenerator;
import com.abc.bank.abc.viewmodels.BankingServiceModel;
import com.abc.bank.abc.viewmodels.MultiCounterBankingServiceModel;
import com.abc.bank.abc.viewmodels.ServicesPlaceholder;
import com.abc.bank.abc.enums.ServiceProcessingType;
import com.abc.bank.abc.enums.TokenServiceStatus;
import com.abc.bank.abc.enums.TokenStatus;
import com.abc.bank.abc.datamodels.*;
import com.abc.bank.abc.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TokenProcessingServiceImpl implements TokenProcessingService {

    @Autowired
    TokenRepository tokenRepository;
    
    @Autowired
    TokenProcessingStepsService tokenProcessingStepsService;

    @Autowired
    TokenMultiCounterServicesService tokenMultiCounterServicesService;

    @Autowired
    TokenServicesService tokenServicesService;

    @Override
    public Token createToken(Token token) {
        List<ServicesPlaceholder> servicesPlaceholderList = token.getBankingServicesPlaceholder();

        if (servicesPlaceholderList == null) {
            throw new IllegalArgumentException("Atleast one service should be requested before creating token");
        }

        Token lastTokenCreated = tokenRepository.getLastTokenCreated();
        Date date = new Date();
        token.setIssuedAt(new Timestamp(date.getTime()));
        if (lastTokenCreated != null &&
                !DateUtility.getInstance().isSameWithCurrentDate(new Date(lastTokenCreated.getIssuedAt().getTime()))) {
            TokenNumberGenerator.getInstance().resetCounter();
        }
        token.setTokenNumber(TokenNumberGenerator.getInstance().getCounter());
        token.setStatus(TokenStatus.ISSUED);
        Token createdToken = tokenRepository.save(token);

        return createTokenServices(createdToken, servicesPlaceholderList);
    }

    private Token createTokenServices(Token createdToken, List<ServicesPlaceholder> servicesPlaceholderList) {

        List<TokenService> tokenServices = new ArrayList<>();
        List<TokenMultiCounterService> multiCounterServices = new ArrayList<>();

        for(int index = 0; index < servicesPlaceholderList.size(); index++) {
            ServicesPlaceholder placeholder = servicesPlaceholderList.get(index);
            if (placeholder.getService().getServiceProcessingType().equals(ServiceProcessingType.MULTI_COUNTER)) {
                MultiCounterBankingServiceModel multiCounterBankingServiceModel = (MultiCounterBankingServiceModel) placeholder.getService();
                MultiCounterBankingService multiCounterBankingService = multiCounterBankingServiceModel.convertToSubEntity();
                TokenMultiCounterService tokenMultiCounterService = new TokenMultiCounterService();
                tokenMultiCounterService.setService(multiCounterBankingService);
                tokenMultiCounterService.setProcessingOrder(placeholder.getOrderOfService());
                tokenMultiCounterService.setStatus(TokenServiceStatus.QUEUED);
                tokenMultiCounterService.setToken(createdToken);
                multiCounterServices.add(tokenMultiCounterServicesService.createTokenMultiCounterService(tokenMultiCounterService));
            } else {
                BankingServiceModel bankingServiceModel = placeholder.getService();
                BankingService bankingService = bankingServiceModel.convertToEntity();
                TokenService tokenService = new TokenService();
                tokenService.setService(bankingService);
                tokenService.setProcessingOrder(placeholder.getOrderOfService());
                tokenService.setStatus(TokenServiceStatus.QUEUED);
                tokenService.setToken(createdToken);
                tokenServices.add(tokenServicesService.createTokenService(tokenService));
            }
        }
        createdToken.setTokenServices(tokenServices);
        createdToken.setTokenMultiCounterServices(multiCounterServices);
        createTokenProcessingSteps(createdToken);
        return createdToken;
    }

    private void createTokenProcessingSteps(Token token) {

        if (token != null && token.getStatus() == TokenStatus.ISSUED) {
            List<TokenService> tokenServices = tokenServicesService.getAllTokenServicesForToken(token.getId());
            List<TokenMultiCounterService> tokenMultiCounterServices = tokenMultiCounterServicesService.getAllTokenMultiCounterServicesForToken(token.getId());

            if (tokenServices != null && tokenMultiCounterServices != null) {
                processBothTypeOfServices(tokenServices, tokenMultiCounterServices);
            } else if (tokenServices != null) {
                processTokenService(tokenServices);
            } else if (tokenMultiCounterServices != null) {
                processTokenMultiCounterService(tokenMultiCounterServices);
            } else {
                throw new IllegalArgumentException("There are no pending services found for which token processing step needs to be created");
            }
        } else {
            throw new IllegalInputException("The token passed to create token processing steps is either null or not in ISSUED state");
        }
    }

    private void processTokenMultiCounterService(List<TokenMultiCounterService> tokenMultiCounterServices) {

        for (int index = 0; index < tokenMultiCounterServices.size(); index++) {
            createTokenProcessingStepsForTokenMultiCounterService(tokenMultiCounterServices.get(index));
        }
    }

    private void processTokenService(List<TokenService> tokenServices) {
        for (int index = 0; index < tokenServices.size(); index++) {
            createTokenProcessingStepsForTokenService(tokenServices.get(index));
        }
    }

    private void processBothTypeOfServices(List<TokenService> tokenServices,
                                                           List<TokenMultiCounterService> tokenMultiCounterServices) {
        int tokenServicesIndex = 0, tokenMultiCounterServicesIndex = 0;
        for ( ; tokenServicesIndex < tokenServices.size() && tokenMultiCounterServicesIndex <tokenMultiCounterServices.size(); ) {
            if (tokenServices.get(tokenServicesIndex).getProcessingOrder() < tokenMultiCounterServices.get(tokenMultiCounterServicesIndex).getProcessingOrder()) {
                createTokenProcessingStepsForTokenService(tokenServices.get(tokenServicesIndex));
                tokenServicesIndex++;
            } else {
                createTokenProcessingStepsForTokenMultiCounterService(tokenMultiCounterServices.get(tokenMultiCounterServicesIndex));
                tokenMultiCounterServicesIndex++;
            }
        }

        if (tokenServicesIndex < tokenServices.size()) {
            for (int index = tokenServicesIndex; index < tokenServices.size(); index++) {
                createTokenProcessingStepsForTokenService(tokenServices.get(index));
            }
        } else {
            for (int index = tokenMultiCounterServicesIndex; index < tokenMultiCounterServices.size(); index++) {
                createTokenProcessingStepsForTokenMultiCounterService(tokenMultiCounterServices.get(index));
            }
        }
    }

    private List<TokenProcessingSteps> createTokenProcessingStepsForTokenMultiCounterService(TokenMultiCounterService tokenMultiCounterService) {

        List<BankingService> bankingServices = tokenMultiCounterService.getService().getBankingServices();
        List<TokenProcessingSteps> tokenProcessingStepsList = new ArrayList<>();

        for (int index = 0; index < bankingServices.size(); index++) {
            TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
            tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
            tokenProcessingSteps.setService(bankingServices.get(index));
            tokenProcessingSteps.setToken(tokenMultiCounterService.getToken());
            tokenProcessingSteps.setServiceId(tokenMultiCounterService.getId());
            tokenProcessingSteps.setStatus(TokenServiceStatus.QUEUED);
            tokenProcessingStepsList.add(tokenProcessingStepsService.createTokenProcessingStep(tokenProcessingSteps));
        }
        return tokenProcessingStepsList;
    }

    private TokenProcessingSteps createTokenProcessingStepsForTokenService(TokenService tokenService) {
        TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
        tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        tokenProcessingSteps.setService(tokenService.getService());
        tokenProcessingSteps.setServiceId(tokenService.getId());
        tokenProcessingSteps.setStatus(TokenServiceStatus.QUEUED);
        tokenProcessingSteps.setToken(tokenService.getToken());
        return tokenProcessingStepsService.createTokenProcessingStep(tokenProcessingSteps);
    }

    @Override
    public Token getToken(Integer tokenId) {
        Optional<Token> optional = tokenRepository.findById(tokenId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResourceNotFoundException(tokenId, "Token not found");
        }
    }

    @Override
    public List<Token> getTokens() {
        return tokenRepository.findAll();
    }

    @Override
    public Token updateToken(Integer tokenId, Token token) {
        if (!tokenRepository.findById(tokenId).isPresent()) {
            throw new ResourceNotFoundException(tokenId, "The token you are trying to update is not present");
        }
        if (token ==  null) {
            throw new NullPointerException("The token parameter passed to update can't be null");
        }
        if (token.getStatus() == TokenStatus.CANCELLED) {
            tokenProcessingStepsService.cancelTokenProcessingStepsForToken(tokenId);
            tokenServicesService.cancelAllServicesForToken(tokenId);
            tokenMultiCounterServicesService.cancelAllMultiCounterServicesForToken(tokenId);
        }
        token.setId(tokenId);
        return tokenRepository.save(token);
    }

    @Override
    public void deleteToken(Integer tokenId) {
        tokenRepository.deleteById(tokenId);
    }

    @Override
    public Token pickToken(Token token) {
        TokenProcessingSteps tokenProcessingSteps = tokenProcessingStepsService.getStatusTokenProcessingStepForToken(token.getId(), TokenServiceStatus.COUNTER_ASSIGNED);

        if (tokenProcessingSteps != null) {
            changeTokenProcessingStepStatus(tokenProcessingSteps, TokenServiceStatus.IN_PROCESS);
        } else {
            throw new IllegalInputException("There are no token services for the token which are assigned to counter");
        }

        changeParentServiceStatusForTokenProcessingStep(tokenProcessingSteps, TokenServiceStatus.IN_PROCESS);

        tokenProcessingStepsService.updateTokenProcessingStep(tokenProcessingSteps);
        token.setStatus(TokenStatus.IN_PROCESS);
        tokenRepository.save(token);
        return  token;
    }

    @Override
    public Token processToken(Integer branchId, Token token, String comments, Employee servedBy) {
        TokenProcessingSteps tokenProcessingStep = tokenProcessingStepsService.getStatusTokenProcessingStepForToken(token.getId(), TokenServiceStatus.IN_PROCESS);

        if (tokenProcessingStep != null) {
            tokenProcessingStep.setActionOrComments(comments);
            tokenProcessingStep.setEmployee(servedBy);
            changeTokenProcessingStepStatus(tokenProcessingStep, TokenServiceStatus.COMPLETED);
        } else {
            throw new IllegalInputException("There are no token services for the token which are assigned to counter");
        }

        TokenProcessingSteps nextTokenProcessingStep = getNextHighestOrderPendingTokenProcessingStep(tokenProcessingStep);

        if (nextTokenProcessingStep == null) {
            changeParentServiceStatusForTokenProcessingStep(tokenProcessingStep, TokenServiceStatus.COMPLETED);
            if (isPendingServicesForToken(token)) {
                changeTokenStatus(token, TokenStatus.ISSUED);
            } else {
                changeTokenStatus(token, TokenStatus.COMPLETED);
            }
        } else {
            changeParentServiceStatusForTokenProcessingStep(tokenProcessingStep, TokenServiceStatus.QUEUED);
            changeTokenStatus(token, TokenStatus.ISSUED);
        }

        return token;
    }

    @Override
    public Token getNextToken(Integer counterId) {
        return tokenRepository.getNextToken(counterId);
    }

    @Override
    public Token getCurrentToken(Integer counterId) {
        return tokenRepository.getCurrentToken(counterId);
    }

    @Override
    public Token pickNextToken(CustomerType customerType, List<BankingService> bankingServices) {
        Token token = tokenRepository.pickNextToken(customerType.toString(), bankingServices);
        if (token != null) {
            TokenProcessingSteps tokenProcessingSteps = tokenProcessingStepsService.getStatusTokenProcessingStepForToken(token.getId(),
                    TokenServiceStatus.QUEUED);
            if (tokenProcessingSteps != null) {
                tokenProcessingSteps.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
                tokenProcessingStepsService.updateTokenProcessingStep(tokenProcessingSteps);
            } else {
                throw new NullPointerException("The token processing step for the token picked is null");
            }
            changeParentServiceStatusForTokenProcessingStep(tokenProcessingSteps, TokenServiceStatus.COUNTER_ASSIGNED);
        }
        return  token;
    }

    private void changeTokenStatus(Token token, TokenStatus tokenStatus) {

        token.setStatus(tokenStatus);
        tokenRepository.save(token);
    }

    private boolean isPendingServicesForToken(Token token) {

        TokenService tokenService = tokenServicesService.getHighestOrderPendingTokenService(token.getId());

        TokenMultiCounterService tokenMultiCounterService = tokenMultiCounterServicesService.getHighestOrderPendingMultiCounterService(token.getId());

        return tokenService != null || tokenMultiCounterService != null;
    }


    private TokenProcessingSteps getNextHighestOrderPendingTokenProcessingStep(TokenProcessingSteps tokenProcessingStep) {
        if (tokenProcessingStep.getServiceProcessingType() == ServiceProcessingType.MULTI_COUNTER) {
            return tokenProcessingStepsService.getHighestOrderPendingTokenProcessingStep(tokenProcessingStep.getServiceId());
        }
        else {
            return null;
        }
    }

    private TokenProcessingSteps changeTokenProcessingStepStatus(TokenProcessingSteps tokenProcessingSteps,
                                                                 TokenServiceStatus tokenServiceStatus) {
        tokenProcessingSteps.setStatus(tokenServiceStatus);
        tokenProcessingStepsService.updateTokenProcessingStep(tokenProcessingSteps);
        return tokenProcessingSteps;
    }

    private void changeParentServiceStatusForTokenProcessingStep(TokenProcessingSteps tokenProcessingSteps,
                                                                 TokenServiceStatus tokenServiceStatus) {

        if (tokenProcessingSteps != null) {
            int serviceId = tokenProcessingSteps.getServiceId();
            ServiceProcessingType serviceProcessingType = tokenProcessingSteps.getServiceProcessingType();
            if (serviceProcessingType.equals(ServiceProcessingType.MULTI_COUNTER)) {

                TokenMultiCounterService tokenMultiCounterService = tokenMultiCounterServicesService.getTokenMultiCounterService(serviceId);
                tokenMultiCounterServicesService.updateTokenMultiCounterServiceStatus(tokenMultiCounterService, tokenServiceStatus);

            } else if (serviceProcessingType.equals(ServiceProcessingType.SINGLE_COUNTER)) {
                TokenService tokenService = tokenServicesService.getTokenService(serviceId);
                tokenServicesService.updateTokenServiceStatus(tokenService, tokenServiceStatus);
            } else {
                throw new IllegalArgumentException("Service Processing type is not matching the expected values");
            }
        } else {
            throw new IllegalArgumentException("Token processing step can't be null");
        }
    }

}
