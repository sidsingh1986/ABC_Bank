package com.abc.bank.abc.Services;

import com.abc.bank.abc.Enums.TokenServiceStatus;
import com.abc.bank.abc.Models.*;
import com.abc.bank.abc.Repositories.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    CounterRepository counterRepository;

    @Autowired
    TokenServiceService tokenServiceService;

    @Autowired
    TokenProcessingService tokenProcessingService;

    @Override
    public Counter createNewCounter(Counter counter) {
        Counter createdCounter = counterRepository.save(counter);
        return createdCounter;
    }

    @Override
    public void updateCounter(Counter counter) {
       counterRepository.save(counter);
    }

    @Override
    public List<Counter> getAllCounters() {
        return counterRepository.findAll();
    }

    @Override
    public Counter getCounter(Integer counterId) {
        return counterRepository.findById(counterId).get();
    }

    @Override
    public List<Token> getAssignedTokens(Integer counterNumber) {
        Counter counter = getCounter(counterNumber);
        if (counter != null) {
            return counter.getTokens();
        }
        return null;
    }

    @Override
    public Token getNextToken(Integer counterId) {
        List<Token> tokens = getAssignedTokens(counterId);
        if(tokens != null && tokens.size() > 0) {
             return tokens.get(0);
        } else {
            System.out.println("did not find any token to process");
        }
        return null;
    }

    @Override
    public void addServiceToCounter(Integer counterId, BankingService bankingService) {
        Counter counter = getCounter(counterId);
        counter.getServicesOffered().add(bankingService);
        counterRepository.save(counter);
    }

    @Override
    public void removeServiceFromCounter(Integer counterId, BankingService bankingService) {
        Counter counter = getCounter(counterId);
        counter.getServicesOffered().remove(bankingService);
        counterRepository.save(counter);
    }

    @Override
    public List<BankingService> listServicesOfferedByCounter(Integer counterId) {
        Counter counter = getCounter(counterId);
        return counter.getServicesOffered();
    }

    @Override
    public void reassignTokenForNextService(Integer tokenId) {
    }

    @Override
    public void pickToken(Integer counterId, Integer tokenId) {

        Token token = tokenProcessingService.getToken(tokenId);
        tokenProcessingService.setTokenStatusToProcess(token);

        TokenService tokenService = tokenServiceService.getHighestOrderTokenService(tokenId);
        tokenServiceService.setTokenStatusToProcess(tokenService);
        token.setCurrentTokenService(tokenService);
    }

    @Override
    public void processToken(Integer counterId, Integer tokenId, String actionOrComments, Employee employee) {
        Token token = tokenProcessingService.getToken(tokenId);

        TokenService tokenService = token.getCurrentTokenService();
        tokenServiceService.addActionOrComments(tokenService, actionOrComments, employee);
    }

    @Override
    public void completeOrForwardToken(Integer branchId, Integer counterId, Integer tokenId, String actionOrComments, Employee employee) {
        List<BankingService> bankingServices = listServicesOfferedByCounter(counterId);

        Token token = tokenProcessingService.getToken(tokenId);

        TokenService tokenService = token.getCurrentTokenService();

        tokenServiceService.completeTokenService(tokenService);

        TokenService nextTokenService = tokenServiceService.getHighestOrderTokenService(tokenId);
        if (bankingServices.contains(nextTokenService.getService())) {
            token.setCurrentTokenService(nextTokenService);
            processToken(counterId, tokenId, actionOrComments, employee);
            completeOrForwardToken(branchId, counterId, tokenId,  actionOrComments, employee);
        } else {
            token.setCurrentTokenService(null);
            tokenProcessingService.assignCounter(tokenId, branchId, nextTokenService.getId());
        }
    }


}
