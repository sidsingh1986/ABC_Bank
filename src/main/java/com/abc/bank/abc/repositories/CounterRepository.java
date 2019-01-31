package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.Counter;
import com.abc.bank.abc.datamodels.Token;
import com.abc.bank.abc.enums.CustomerType;
import com.abc.bank.abc.enums.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CounterRepository extends JpaRepository<Counter, Integer> {

    @Query(value = "select coun from Counter coun join Counter_Services coun_ser where coun. Branch_id = :branchId" +
            " AND coun.type_of_service = :customerType AND coun_serv = :serviceId", nativeQuery = true)
    List<Counter> getCountersForService(Integer branchId, Integer serviceId, CustomerType customerType);

    @Query(value = "select token from Token token where token.Counter_id = :counterId " +
            "AND token.status = :'COUNTER_ASSIGNED' order by processing_order limit 1", nativeQuery = true)
    Token getNextToken(Integer counterId);

    @Query(value = "select serv from Service join Counter_Services coun_serv on serv.id = coun_serv.Service_id " +
            "where coun_serv.Counter_id = :counterId AND coun_serv.Service_id = :serviceId AND serv.id = :serviceId", nativeQuery = true)
    BankingService getServiceForCounter(Integer counterId, Integer serviceId);

    @Query(value = "select token from Token token where token.Counter_id = :counterId " +
            "AND token.status = :'IN_PROCESS'", nativeQuery = true)
    Token getCurrentToken(Integer counterId);
}
