package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.Counter;
import com.abc.bank.abc.Models.Employee;
import com.abc.bank.abc.Models.Token;

import java.util.List;

public interface CounterService {
    Counter createNewCounter(Counter counter);

    void updateCounter(Counter counter);

    List<Counter> getAllCounters();

    Counter getCounter(Integer counterId);

    List<Token> getAssignedTokens(Integer counterNumber);

    Token getNextToken(Integer counterId);

    void addServiceToCounter(Integer counterId, BankingService bankingService);

    void removeServiceFromCounter(Integer counterId, BankingService bankingService);

    List<BankingService> listServicesOfferedByCounter(Integer counterId);

    BankingService getServiceOfferedByCounter(Integer counterId, Integer serviceId);
}
