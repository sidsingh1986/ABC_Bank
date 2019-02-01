package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.MultiCounterBankingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MultiCounterRepository extends JpaRepository<MultiCounterBankingService, Integer> {

    @Query(value = "select multi_counter.* from Multi_counter_service multi_counter join Branch_Multi_counter_services br_services on multI_counter.id = br_services.Multi_counter_service_id where br_services.Branch_id = :branchId" +
            " AND br_services.Multi_counter_service_id = :serviceId AND multi_counter.id = :serviceId", nativeQuery = true)
    MultiCounterBankingService getMultiCounterBankingServiceforBranch(Integer branchId, Integer serviceId);
}
