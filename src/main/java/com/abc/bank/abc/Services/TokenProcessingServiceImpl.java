package com.abc.bank.abc.Services;

import com.abc.bank.abc.Enums.TokenServiceStatus;
import com.abc.bank.abc.Enums.TokenStatus;
import com.abc.bank.abc.Enums.TypeOfService;
import com.abc.bank.abc.Models.*;
import com.abc.bank.abc.Repositories.TokenRepository;
import com.abc.bank.abc.Repositories.TokenServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenProcessingServiceImpl implements TokenProcessingService {

    @Autowired
    BranchService branchService;

    @Autowired
    TokenServiceRepository tokenServiceRepository;

    @Autowired
    TokenServiceService tokenServiceService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    CounterService counterService;


    @Override
    public Token createToken(Customer customer, ServicesPlaceholder servicesPlaceholder, int branchId) {
        Token token = new Token();
        token.setCustomer(customer);
        token.setStatus(TokenStatus.ISSUED);
        List<TokenService> tokenServices = tokenServiceService.generateTokenServices(servicesPlaceholder);
        token.setTokenServices(tokenServices);
        return tokenRepository.save(token);
    }


    @Override
    public void completeToken(Token token) {
        List<TokenService> tokenServices = token.getTokenServices();
        for (int index = 0; index < tokenServices.size(); index++) {
            if (tokenServices.get(index).getStatus() != TokenServiceStatus.CANCELLED || tokenServices.get(index).getStatus() != TokenServiceStatus.COMPLETED) {
                System.out.println("All services in token are not completed or cancelled so token should not be completed");
                return;
            }
        }
        token.setStatus(TokenStatus.COMPLETED);
        tokenRepository.save(token);
    }

    @Override
    public void cancelToken(Token token) {
        List<TokenService> tokenServices = token.getTokenServices();
        for (int index = 0; index < tokenServices.size(); index++) {
            tokenServiceService.cancelTokenService(tokenServices.get(index));
        }
        token.setStatus(TokenStatus.CANCELLED);
        tokenRepository.save(token);
    }

    @Override
    public void assignCounter(Integer tokenId, Integer branchId, Integer serviceId, TypeOfService typeOfService) {
        int counterIndex = 0;
        List<Counter> counters = branchService.getCountersForService(branchId, serviceId, typeOfService);
        for(int index = 0; index < counters.size(); index++) {
            int counterQueue = counters.get(index).getTokens().size();
            if (counters.get(counterIndex).getTokens().size() > counterQueue) {
                counterIndex = index;
            }
        }
        Counter counter = counters.get(counterIndex);
        TokenService tokenService = tokenServiceService.getHighestOrderTokenService(tokenId);
        tokenService.setCounter(counter);
        tokenService.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        tokenServiceRepository.save(tokenService);
        counter.getTokens().add(getToken(tokenId));
        counterService.updateCounter(counter);
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
    public void setTokenStatusToProcess(Token token) {
        token.setStatus(TokenStatus.IN_PROCESS);
        tokenRepository.save(token);
    }
}
