package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.Branch;
import com.abc.bank.abc.datamodels.Counter;
import com.abc.bank.abc.datamodels.MultiCounterBankingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

    @Query(value = "select service from services service join Branch_Services br_services where br_services.Branch_id = :branchId " +
            "AND br_services.Services_id = :serviceId, service.id = :serviceId", nativeQuery = true)
    BankingService findBankingService(Integer branchId, Integer serviceId);

    @Query(value = "select multi_counter from Multi_counter_service join Branch_Multi_counter_services br_services where br_services.Branch_id = :branchId" +
            "AND br_services.Services_id = :serviceId, service.id = :serviceId", nativeQuery = true)
    MultiCounterBankingService findMultiCounterBankingService(Integer branchId, Integer serviceId);

    @Query(value = "select counter from Counter where counter.Branch_id = :branchId AND counter.id = :counterNumber", nativeQuery = true)
    Counter getCounter(Integer branchId, Integer counterNumber);
}
