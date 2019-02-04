package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.Counter;
import com.abc.bank.abc.datamodels.Token;
import com.abc.bank.abc.enums.CustomerType;
import com.abc.bank.abc.enums.TokenStatus;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CounterService {
    /**
     * For creating a new counter
     *
     * @param counter counter instance to be created
     * @return created counter instance
     */
    Counter createNewCounter(Counter counter);

    /**
     * For updating a counter and creates it if the counter does not exists
     *
     * @param counterId
     * @param counter counter instance to be updated
     */
    Counter updateCounter(Integer counterId, Counter counter);

    /**
     * For getting the list of all counters
     *
     * @return list of all counters
     */
    List<Counter> getAllCounters();

    /**
     * For getting a particular counter
     *
     * @param counterId counter identifier
     * @return counter with the counter id
     * @throws ResourceNotFoundException if the counter with passed id is not found
     */
    Counter getCounter(Integer counterId);

    /**
     * For viewing the tokens currently assigned to counter
     *
     * @param counterNumber counter identifier
     * @return list of token
     */
    List<Token> getAssignedTokens(Integer counterNumber);

    /**
     * For getting the next token which will be processed by counter. It changes the status of
     * token to IN_PROCESS.
     *
     * @param counterId counter identifier
     * @return Token to process
     */
    Token getNextToken(Integer counterId);

    /**
     * For adding a services to services served by  a counter
     *
     * @param counterId counter identifier
     * @param bankingService banking service instance
     */
    void addServiceToCounter(Integer counterId, BankingService bankingService);

    /**
     * For removing a service from the services currently served by a counter
     *
     * @param counterId counter identifier
     * @param bankingService banking service instance
     */
    void removeServiceFromCounter(Integer counterId, BankingService bankingService);

    /**
     * For getting the list of services currently served by a counter
     *
     * @param counterId counter identifier
     * @return  list of banking services served by a particular counter
     */
    List<BankingService> listServicesOfferedByCounter(Integer counterId);

    /**
     * For viewing a service currently served by a counter
     *
     * @param counterId counter identifier
     * @param serviceId banking service identifier
     * @return banking service
     * @throws ResourceNotFoundException if the service is not served by counter
     */
    BankingService getServiceOfferedByCounter(Integer counterId, Integer serviceId);

    /**
     * For getting the token which is currently getting processed by counter.
     *
     * @param counterId counter identifier
     * @return Token which is getting processed
     */
    Token getCurrentToken(Integer counterId);

    /**
     * Gets the counters of branch serving the particular service
     *
     * @param branchId branch identifier
     * @param serviceId service identifer
     * @param customerType type of customer(REGULAR,PRIORITY)
     * @return branch with counter added
     * @throws ResourceNotFoundException when branch does not exist or
     * counter with passed id is not present in branch
     */
    List<Counter> getCountersForService(Integer branchId, Integer serviceId, CustomerType customerType);

    /**
     * For getting a particular counter for a branch
     *
     * @param branchId branch identifier
     * @param counterNumber counter identifier
     * @return counter with the counter id
     * @throws ResourceNotFoundException if the counter with passed id is not found
     */
    Counter getCounterForBranch(Integer branchId, Integer counterNumber);

    /**
     * For picking a particular token by a counter
     *
     * @param token token to pick
     * @return Token picked with status change
     */
    Token pickToken(Token token);

    /**
     * For picking the next token by a counter
     *
     * @param counterId counter identifier
     * @return Token picked with status change
     */
    Token pickNextToken(Integer counterId);
}
