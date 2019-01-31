package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.BankingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceRepository extends JpaRepository<BankingService, Integer> {

    @Query(value = "select service.* from Services service join Multi_counter_service_has_Services multi_ser where service.id = :serviceId" +
            " AND multi_ser.Services_id = :serviceId AND multi_ser.Multi_counter_service_id = :multiCounterServiceId", nativeQuery = true)
    BankingService getServiceForMultiCounterService(Integer multiCounterServiceId, Integer serviceId);

    @Query(value = "select service.* from Services service join Branch_Services br_services where br_services.Branch_id = :branchId " +
            "AND br_services.Services_id = :serviceId AND service.id = :serviceId", nativeQuery = true)
    BankingService getServiceforBranch(Integer branchId, Integer serviceId);

    @Query(value = "select serv.* from Service serv join Counter_Services coun_serv where " +
            "coun_serv.Counter_id = :counterId AND coun_serv.Service_id = :serviceId AND serv.id = :serviceId", nativeQuery = true)
    BankingService getServiceForCounter(Integer counterId, Integer serviceId);
}
