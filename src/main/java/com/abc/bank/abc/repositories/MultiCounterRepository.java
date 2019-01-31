package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.MultiCounterBankingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MultiCounterRepository extends JpaRepository<MultiCounterBankingService, Integer> {

    @Query(value = "select service.* from Services service join Multi_counter_service_has_Services multi_ser where service.id = :serviceId" +
            " AND multi_ser.Services_id = :serviceId AND multi_ser.Multi_counter_service_id = :multiCounterServiceId", nativeQuery = true)
    BankingService getService(Integer multiCounterServiceId, Integer serviceId);
}
