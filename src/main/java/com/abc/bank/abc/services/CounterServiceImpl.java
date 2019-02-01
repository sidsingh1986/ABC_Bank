package com.abc.bank.abc.services;

import com.abc.bank.abc.enums.CustomerType;
import com.abc.bank.abc.enums.TokenStatus;
import com.abc.bank.abc.exceptions.IllegalInputException;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.datamodels.*;
import com.abc.bank.abc.repositories.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    CounterRepository counterRepository;

    @Autowired
    TokenProcessingService tokenProcessingService;

    @Autowired
    ServicesSevice servicesSevice;

    @Override
    public Counter createNewCounter(Counter counter) {
        return counterRepository.save(counter);
    }

    @Override
    public Counter updateCounter(Integer counterId, Counter counter) {
        Optional<Counter> optional = counterRepository.findById(counterId);

        if(!optional.isPresent()) {
            throw new ResourceNotFoundException(counterId, "Counter not found");
        }
        counter.setId(counterId);
       return counterRepository.save(counter);
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
        return tokenProcessingService.getNextToken(counterId);
    }

    @Override
    public void addServiceToCounter(Integer counterId, BankingService bankingService) {
        Counter counter = getCounter(counterId);
        if (!counter.addService(bankingService)) {
            throw new IllegalInputException("The banking service can't be added to counter");
        }
        counterRepository.save(counter);
    }

    @Override
    public void removeServiceFromCounter(Integer counterId, BankingService bankingService) {
        Counter counter = getCounter(counterId);
        if (!counter.removeService(bankingService)) {
            throw new IllegalInputException("The service is not present in the counter services list");
        }
        counterRepository.save(counter);
    }

    @Override
    public List<BankingService> listServicesOfferedByCounter(Integer counterId) {
        Counter counter = getCounter(counterId);
        return counter.getServicesOffered();
    }

    @Override
    public BankingService getServiceOfferedByCounter(Integer counterId, Integer serviceId) {
        return servicesSevice.getServiceForCounter(counterId, serviceId);
    }

    @Override
    public Token getCurrentToken(Integer counterId) {
        Token token = tokenProcessingService.getCurrentToken(counterId);

        if (token == null) {
            throw new IllegalInputException("There is no token which is being processed for this counter");
        }
        return token;
    }

    @Override
    public List<Counter> getCountersForService(Integer branchId, Integer serviceId, CustomerType customerType) {

        return counterRepository.getCountersForService(branchId, serviceId, customerType);

    }

    @Override
    public Counter getCounterForBranch(Integer branchId, Integer counterNumber) {
        return counterRepository.getCounterForBranch(branchId, counterNumber);
    }
}
