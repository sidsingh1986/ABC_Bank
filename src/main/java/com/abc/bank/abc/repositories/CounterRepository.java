package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.Counter;
import com.abc.bank.abc.enums.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CounterRepository extends JpaRepository<Counter, Integer> {

    @Query(value = "select coun.* from Counter coun join Counter_Services coun_ser where coun.Branch_id = :branchId" +
            " AND coun.type_of_service = :customerType AND coun_serv.Service_id = :serviceId", nativeQuery = true)
    List<Counter> getCountersForService(Integer branchId, Integer serviceId, CustomerType customerType);

    @Query(value = "select counter.* from Counter counter where counter.Branch_id = :branchId AND counter.id = :counterNumber", nativeQuery = true)
    Counter getCounterForBranch(Integer branchId, Integer counterNumber);
}
