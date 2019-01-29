package com.abc.bank.abc.Services;

import com.abc.bank.abc.ViewModels.BankingServiceModel;
import com.abc.bank.abc.ViewModels.MultiCounterBankingServiceModel;
import com.abc.bank.abc.ViewModels.ServicesPlaceholder;
import com.abc.bank.abc.Enums.ServiceProcessingType;
import com.abc.bank.abc.Enums.TokenServiceStatus;
import com.abc.bank.abc.Enums.TokenStatus;
import com.abc.bank.abc.DataModels.*;
import com.abc.bank.abc.Repositories.TokenRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TokenProcessingServiceImpl implements TokenProcessingService {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    BranchService branchService;
    
    @Autowired
    TokenProcessingStepsService tokenProcessingStepsService;

    @Override
    public Token createToken(Token token) {
        List<TokenMultiCounterService> tokenMultiCounterServices = new ArrayList<>();
        List<TokenService> tokenServices = new ArrayList<>();
        List<ServicesPlaceholder> servicesPlaceholderList = token.getBankingServicesPlaceholder();

        if (servicesPlaceholderList == null)
            throw new IllegalArgumentException("Atleast one service should be requested before creating token");

        for(int index = 0; index < servicesPlaceholderList.size(); index++) {
            ServicesPlaceholder placeholder = servicesPlaceholderList.get(index);
            if (placeholder.getService().getServiceProcessingType().equals(ServiceProcessingType.MULTI_COUNTER)) {
                MultiCounterBankingServiceModel multiCounterBankingServiceModel = (MultiCounterBankingServiceModel) placeholder.getService();
                MultiCounterBankingService multiCounterBankingService = multiCounterBankingServiceModel.convertToEntity();
                TokenMultiCounterService tokenMultiCounterService = new TokenMultiCounterService();
                tokenMultiCounterService.setService(multiCounterBankingService);
                tokenMultiCounterService.setProcessingOrder(placeholder.getOrderOfService());
                tokenMultiCounterService.setStatus(TokenServiceStatus.QUEUED);
                tokenMultiCounterServices.add(tokenMultiCounterService);
            } else {
                BankingServiceModel bankingServiceModel = (BankingServiceModel) placeholder.getService();
                BankingService bankingService = bankingServiceModel.convertToEntity();
                TokenService tokenService = new TokenService();
                tokenService.setService(bankingService);
                tokenService.setProcessingOrder(placeholder.getOrderOfService());
                tokenService.setStatus(TokenServiceStatus.QUEUED);
                tokenServices.add(tokenService);
            }
        }

        Collections.sort(tokenMultiCounterServices);
        Collections.sort(tokenServices);
        token.setTokenServices(tokenServices);
        token.setTokenMultiCounterServices(tokenMultiCounterServices);
        token.setStatus(TokenStatus.ISSUED);
        return tokenRepository.save(token);
    }

    @Override
    public void assignCounter(Integer tokenId, Integer branchId) {
        Token token = getToken(tokenId);
        List<TokenService> tokenServices = token.getTokenServices();
        List<TokenMultiCounterService> tokenMultiCounterServices = token.getTokenMultiCounterServices();

        Collections.sort(tokenServices);
        Collections.sort(tokenMultiCounterServices);

        TokenService tokenService = null;

        TokenMultiCounterService tokenMultiCounterService = null;

        for (TokenService tokenService1 : tokenServices) {
            tokenService = tokenService1;
            if (tokenService.getStatus().equals(TokenServiceStatus.QUEUED))
                break;
        }

        for (TokenMultiCounterService tokenMultiCounterService1 : tokenMultiCounterServices) {
            tokenMultiCounterService = tokenMultiCounterService1;
            if (tokenMultiCounterService.getStatus().equals(TokenServiceStatus.QUEUED))
                break;
        }

        if (tokenService != null && tokenMultiCounterService != null) {
            if ( tokenService.getProcessingOrder() < tokenMultiCounterService.getProcessingOrder()) {
                updateTokenService(branchId, tokenService, token);
            } else {
                updateTokenMultiCounterService(branchId, tokenMultiCounterService, token);
            }
        } else if (tokenService != null) {
            updateTokenService(branchId, tokenService, token);
        } else if ( tokenMultiCounterService != null) {
            updateTokenMultiCounterService(branchId, tokenMultiCounterService, token);
        } else {
            throw new IllegalArgumentException("There are no pending services found in the token");
        }
    }

    @Override
    public Token getToken(Integer tokenId) {
        return tokenRepository.findById(tokenId).get();
    }

    @Override
    public List<Token> getTokens() {
        return tokenRepository.findAll();
    }

    @Override
    public void updateToken(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public void deleteToken(Integer tokenId) {
        tokenRepository.deleteById(tokenId);
    }

    @Override
    public Token pickToken(Token token) {
        List<TokenProcessingSteps> tokenProcessingSteps = tokenProcessingStepsService.getTokenProcessingStepsForToken(token.getId());

        TokenProcessingSteps tokenProcessingStep = null;
        for (TokenProcessingSteps tokenProcessingStep1 : tokenProcessingSteps) {
            tokenProcessingStep = tokenProcessingStep1;
            if (tokenProcessingStep.getStatus().equals(TokenServiceStatus.COUNTER_ASSIGNED)) {
                tokenProcessingStep.setStatus(TokenServiceStatus.IN_PROCESS);
                break;
            }
        }

        if (tokenProcessingStep != null) {
            int serviceId = tokenProcessingStep.getServiceId();
            ServiceProcessingType serviceProcessingType = tokenProcessingStep.getServiceProcessingType();
            if (serviceProcessingType.equals(ServiceProcessingType.MULTI_COUNTER)) {
                List<TokenMultiCounterService> tokenMultiCounterServices = token.getTokenMultiCounterServices();

                for (TokenMultiCounterService tokenMultiCounterService : tokenMultiCounterServices) {
                    if (tokenMultiCounterService.getService().getId() == serviceId) {
                        tokenMultiCounterService.setStatus(TokenServiceStatus.IN_PROCESS);
                    }
                }
            } else if (serviceProcessingType.equals(ServiceProcessingType.SINGLE_COUNTER)) {
                List<TokenService> tokenServices = token.getTokenServices();

                for (TokenService tokenService : tokenServices) {
                    if (tokenService.getService().getId() == serviceId) {
                        tokenService.setStatus(TokenServiceStatus.IN_PROCESS);
                    }
                }
            } else {
                throw new IllegalArgumentException("Service Processing type is not matching the expected values");
            }
        } else {
            throw new IllegalArgumentException("Token processing step can't be null");
        }

        tokenProcessingStepsService.updateTokenProcessingStep(tokenProcessingStep);
        token.setStatus(TokenStatus.IN_PROCESS);
        updateToken(token);
        return  token;
    }

    @Override
    public Token processToken(Integer branchId, Token token, String comments, Employee servedBy) {
        List<TokenProcessingSteps> tokenProcessingSteps = tokenProcessingStepsService.getTokenProcessingStepsForToken(token.getId());
        TokenProcessingSteps tokenProcessingStep = getCurrentTokenProcessingStep(token);

        tokenProcessingStep.setStatus(TokenServiceStatus.COMPLETED);
        tokenProcessingStep.setActionOrComments(comments);
        tokenProcessingStep.setEmployee(servedBy);

        tokenProcessingStepsService.updateTokenProcessingStep(tokenProcessingStep);

        ServiceProcessingType serviceProcessingType = tokenProcessingStep.getServiceProcessingType();
        int serviceId = tokenProcessingStep.getServiceId();

        tokenProcessingStep = null;

        for (int index = 0; index < tokenProcessingSteps.size(); index++) {
            if (tokenProcessingSteps.get(index).getStatus().equals(TokenServiceStatus.QUEUED))
                tokenProcessingStep = tokenProcessingSteps.get(index);
            break;
        }

        if (tokenProcessingStep != null) {
            BankingService bankingService = tokenProcessingStep.getService();
            List<Counter> counters = branchService.getCountersForService(branchId,
                    bankingService.getId(), token.getCustomer().getCustomerType());

            Counter counter = getMinQueueCounter(counters);
            tokenProcessingStep.setCounter(counter);
            tokenProcessingStep.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);

            if(serviceProcessingType == ServiceProcessingType.MULTI_COUNTER) {
                List<TokenMultiCounterService> tokenMultiCounterServices = token.getTokenMultiCounterServices();
                Collections.sort(tokenMultiCounterServices);

                TokenMultiCounterService tokenMultiCounterService = null;
                for (int index = 0; index < tokenMultiCounterServices.size(); index++) {
                    tokenMultiCounterService = tokenMultiCounterServices.get(index);
                    if (tokenMultiCounterService.getId() == serviceId) {
                        tokenMultiCounterService.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
                        break;
                    }
                }

                if (tokenMultiCounterService != null) {
                    tokenProcessingStepsService.updateTokenProcessingStep(tokenProcessingStep);
                    token.setStatus(TokenStatus.COUNTER_ASSIGNED);
                    updateToken(token);
                } else {
                    throw new IllegalArgumentException("The service to process is not present in counter services");
                }
            } else {
                throw new IllegalArgumentException("There should not be multiple token service for single counter service");
            }
        } else {
            if(serviceProcessingType == ServiceProcessingType.MULTI_COUNTER) {
                List<TokenMultiCounterService> tokenMultiCounterServices = token.getTokenMultiCounterServices();
                Collections.sort(tokenMultiCounterServices);
                TokenMultiCounterService tokenMultiCounterService = null;

                for (int index = 0; index < tokenMultiCounterServices.size(); index++) {
                    tokenMultiCounterService = tokenMultiCounterServices.get(index);
                    if (tokenMultiCounterService.getId() == serviceId) {
                        tokenMultiCounterService.setStatus(TokenServiceStatus.COMPLETED);
                        break;
                    }
                }
            } else if (serviceProcessingType == ServiceProcessingType.SINGLE_COUNTER) {
                List<TokenService> tokenServices = token.getTokenServices();
                Collections.sort(tokenServices);
                TokenService tokenService = null;

                for (TokenService tokenService1 : tokenServices) {
                    tokenService = tokenService1;
                    if (tokenService.getId() == serviceId) {
                        tokenService.setStatus(TokenServiceStatus.COMPLETED);
                        break;
                    }
                }

            } else {
                throw new IllegalArgumentException("The service processing type should be either Single counter or Multi Counter");
            }

            if (checkForAllTokenServicesCompletion(token)) {
                token.setStatus(TokenStatus.COMPLETED);
            } else {
                token.setStatus(TokenStatus.ISSUED);
            }
            updateToken(token);
        }
        return token;
    }

    private TokenProcessingSteps getCurrentTokenProcessingStep(Token token) {
        List<TokenProcessingSteps> tokenProcessingSteps = tokenProcessingStepsService.getTokenProcessingStepsForToken(token.getId());

        for(TokenProcessingSteps tokenProcessingStep : tokenProcessingSteps) {
            if (tokenProcessingStep.getStatus() == TokenServiceStatus.IN_PROCESS ) {
                return tokenProcessingStep;
            }
        }

        throw new IllegalStateException("The should have atlease one token processing step in process");
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


    private void updateTokenService(Integer branchId, TokenService tokenService, Token token) {
        TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
        tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        tokenProcessingSteps.setService(tokenService.getService());
        List<Counter> counters = branchService.getCountersForService(branchId,
                tokenService.getService().getId(), token.getCustomer().getCustomerType());

        Counter counter = getMinQueueCounter(counters);
        tokenProcessingSteps.setCounter(counter);
        tokenProcessingSteps.setServiceId(tokenService.getId());
        tokenProcessingSteps.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        tokenProcessingSteps.setToken(token);
        tokenService.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        token.setStatus(TokenStatus.COUNTER_ASSIGNED);
    }

    private void updateTokenMultiCounterService(Integer branchId, TokenMultiCounterService tokenMultiCounterService, Token token) {
        List<BankingService> bankingServices = tokenMultiCounterService.getService().getBankingServices();

        for (int index = 0; index < bankingServices.size(); index++) {
            TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
            tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
            tokenProcessingSteps.setService(bankingServices.get(index));
            tokenProcessingSteps.setToken(token);
            tokenProcessingSteps.setServiceId(tokenMultiCounterService.getId());
            if (index == 0) {
                tokenProcessingSteps.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
                List<Counter> counters = branchService.getCountersForService(branchId,
                        bankingServices.get(index).getId(), token.getCustomer().getCustomerType());

                Counter counter = getMinQueueCounter(counters);
                tokenProcessingSteps.setCounter(counter);
            } else {
                tokenProcessingSteps.setStatus(TokenServiceStatus.QUEUED);
            }
        }
        tokenMultiCounterService.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        token.setStatus(TokenStatus.COUNTER_ASSIGNED);
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


}
