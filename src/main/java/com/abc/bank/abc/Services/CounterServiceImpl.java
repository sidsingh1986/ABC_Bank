package com.abc.bank.abc.Services;

import com.abc.bank.abc.Enums.TokenServiceStatus;
import com.abc.bank.abc.Enums.TokenStatus;
import com.abc.bank.abc.Exceptions.ResourceNotFoundException;
import com.abc.bank.abc.Models.*;
import com.abc.bank.abc.Repositories.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    CounterRepository counterRepository;

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
        Optional<Counter> optional = counterRepository.findById(counterId);

        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResourceNotFoundException(counterId, "Counter not found");
        }
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
    public BankingService getServiceOfferedByCounter(Integer counterId, Integer serviceId) {
        Counter counter = getCounter(counterId);
        List<BankingService> bankingServices = counter.getServicesOffered();

        for (int index = 0; index < bankingServices.size(); index++) {
            if(serviceId == bankingServices.get(index).getId()) {
                return bankingServices.get(index);
            }
        }
        throw new ResourceNotFoundException(serviceId, "service not found counter");
    }
}
