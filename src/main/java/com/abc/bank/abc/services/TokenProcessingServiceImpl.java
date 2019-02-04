package com.abc.bank.abc.services;

import com.abc.bank.abc.enums.CustomerType;
import com.abc.bank.abc.exceptions.IllegalInputException;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;
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

import java.util.ArrayList;
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

    @Autowired
    CounterService counterService;

    @Override
    public Token createToken(Token token) {
        List<ServicesPlaceholder> servicesPlaceholderList = token.getBankingServicesPlaceholder();

        if (servicesPlaceholderList == null) {
            throw new IllegalArgumentException("Atleast one service should be requested before creating token");
        }

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

        return createdToken;
    }

    @Override
    public Token assignCounter(Integer tokenId, Integer branchId) {
        Token token = getToken(tokenId);

        if (token != null && token.getStatus() == TokenStatus.ISSUED) {
            TokenService tokenService = tokenServicesService.getHighestOrderPendingTokenService(tokenId);

            TokenMultiCounterService tokenMultiCounterService = tokenMultiCounterServicesService.getHighestOrderPendingMultiCounterService(token.getId());

            if (tokenService != null && tokenMultiCounterService != null) {
                if (tokenService.getProcessingOrder() < tokenMultiCounterService.getProcessingOrder()) {
                    return assignCounterToTokenService(branchId, tokenService, token);
                } else {
                    return assignCounterToTokenMultiCounterService(branchId, tokenMultiCounterService, token);
                }
            } else if (tokenService != null) {
                return assignCounterToTokenService(branchId, tokenService, token);
            } else if (tokenMultiCounterService != null) {
                return assignCounterToTokenMultiCounterService(branchId, tokenMultiCounterService, token);
            } else {
                throw new IllegalArgumentException("There are no pending services found in the token which you are trying to assign");
            }
        } else {
            throw new IllegalInputException("Cannot assign counter because token is not valid or not in issued state");
        }
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
        return  tokenRepository.pickNextToken(customerType.toString(), bankingServices);
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

    private boolean checkForAllTokenServicesCompletion(Token token) {

        List<TokenMultiCounterService> tokenMultiCounterServices = token.getTokenMultiCounterServices();

        List<TokenService> tokenServices = token.getTokenServices();

        for (TokenService tokenService : tokenServices) {
            if (tokenService.getStatus() != TokenServiceStatus.COMPLETED)
                return false;
        }

        for (TokenMultiCounterService tokenMultiCounterService : tokenMultiCounterServices) {
            if (tokenMultiCounterService.getStatus() != TokenServiceStatus.COMPLETED)
                return false;
        }

        return true;
    }


    private Token assignCounterToTokenService(Integer branchId, TokenService tokenService, Token token) {
        TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
        tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        tokenProcessingSteps.setService(tokenService.getService());
        Counter counter = getBestCounterForService(branchId, tokenService.getService(), token);
        if (counter != null) {
            tokenProcessingSteps.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
            tokenProcessingSteps.setCounter(counter);
        } else {
            throw new IllegalInputException("The counter could not be assigned assigned at this time");
        }
        tokenProcessingSteps.setCounter(counter);
        tokenProcessingSteps.setServiceId(tokenService.getId());
        tokenProcessingSteps.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        tokenProcessingSteps.setToken(token);
        tokenProcessingStepsService.createTokenProcessingStep(tokenProcessingSteps);
        tokenServicesService.updateTokenServiceStatus(tokenService, TokenServiceStatus.COUNTER_ASSIGNED);
        token.setStatus(TokenStatus.COUNTER_ASSIGNED);
        return tokenRepository.save(token);
    }

    private Token assignCounterToTokenMultiCounterService(Integer branchId, TokenMultiCounterService tokenMultiCounterService, Token token) {
        List<BankingService> bankingServices = tokenMultiCounterService.getService().getBankingServices();

        for (int index = 0; index < bankingServices.size(); index++) {
            TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
            tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
            tokenProcessingSteps.setService(bankingServices.get(index));
            tokenProcessingSteps.setToken(token);
            tokenProcessingSteps.setServiceId(tokenMultiCounterService.getId());
            if (index == 0) {
                Counter counter = getBestCounterForService(branchId, bankingServices.get(index), token);
                if (counter != null) {
                    tokenProcessingSteps.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
                    tokenProcessingSteps.setCounter(counter);
                } else {
                    throw new IllegalInputException("The counter could not be assigned assigned at this time");
                }
            } else {
                tokenProcessingSteps.setStatus(TokenServiceStatus.QUEUED);
            }
            tokenProcessingStepsService.createTokenProcessingStep(tokenProcessingSteps);
        }
        tokenMultiCounterServicesService.updateTokenMultiCounterServiceStatus(tokenMultiCounterService, TokenServiceStatus.COUNTER_ASSIGNED);
        token.setStatus(TokenStatus.COUNTER_ASSIGNED);
        return tokenRepository.save(token);
    }

    private Counter getBestCounterForService(Integer branchId, BankingService bankingService, Token token) {
        List<Counter> counters = counterService.getCountersForService(branchId,
                bankingService.getId(), token.getCustomer().getCustomerType());

        Counter counter = getMinQueueCounter(counters);

        return counter;
    }
    private Counter getMinQueueCounter(List<Counter> counters) {
        int numCounters = counters.size();

        int minQueue = Integer.MAX_VALUE;

        Counter bestCounter = null;

        for (int index = 0; index < numCounters; index++) {
            Counter counter = counters.get(index);
            if (minQueue > counter.getTokens().size())
                bestCounter = counter;
        }

        return bestCounter;
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
