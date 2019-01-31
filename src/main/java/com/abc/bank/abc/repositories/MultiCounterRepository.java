package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.MultiCounterBankingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MultiCounterRepository extends JpaRepository<MultiCounterBankingService, Integer> {

    @Query(value = "select service.* from Multi_counter_service service where service.id = :multiCounterServiceId", nativeQuery = true)
    MultiCounterBankingService getMultiCounterBankingService(Integer multiCounterServiceId);

    @Query(value = "select multi_counter from Multi_counter_service join Branch_Multi_counter_services br_services where br_services.Branch_id = :branchId" +
            "AND br_services.Services_id = :serviceId, service.id = :serviceId", nativeQuery = true)
    MultiCounterBankingService findMultiCounterBankingServiceforBranch(Integer branchId, Integer serviceId);
}
