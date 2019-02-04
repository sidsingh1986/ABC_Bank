package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ServicesSevice {

    /**
     * For getting a particular Banking Service
     *
     * @param serviceId service identifier
     * @return service with service Id
     * @throws ResourceNotFoundException if the Banking service for the id is not found
     */
    BankingService getService(Integer serviceId);

    /**
     * For creating a Banking Service
     *
     * @param bankingService banking service instance to be created
     * @return created banking service instance
     */
    BankingService createService(BankingService bankingService);

    /**
     * For getting the list of all Banking services in the system
     *
     * @return list of all banking services
     */
    List<BankingService> getServices();

    /**
     * For updating a Banking service or creating it if the Banking service does not exists
     *
     * @param serviceId
     * @param bankingService banking service to be updated
     */
    BankingService updateService(Integer serviceId, BankingService bankingService);

    /**
     * For deleting a particular Banking Service
     *
     * @param serviceId service identifier
     */
    void deleteService(Integer serviceId);

    /**
     * For getting a particular Banking Service of a multi counter banking service
     *
     * @param multiCounterServiceId multi counter service identifier
     * @param serviceId service identifier
     * @return service with service Id
     * @throws ResourceNotFoundException if the Banking service is not part of multi counter service
     */
    BankingService getServiceForMultiCounterService(Integer multiCounterServiceId, Integer serviceId);

    /**
     * For getting a particular Banking Service for a branch
     *
     * @param branchId branch identifier
     * @param serviceId service identifier
     * @return service with service Id
     * @throws ResourceNotFoundException if the Banking service is not served by branch
     */
    BankingService getBankingServiceForBranch(Integer branchId, Integer serviceId);

    /**
     * For getting a particular Banking Service for a counter
     *
     * @param counterId counter identifier
     * @param serviceId service identifier
     * @return service with service Id
     * @throws ResourceNotFoundException if the Banking service is not found served by counter
     */
    BankingService getServiceForCounter(Integer counterId, Integer serviceId);


    /**
     * For getting all Banking Services served by a counter
     *
     * @param counterId counter identifier
     * @return service with service Id
     * @throws ResourceNotFoundException if the Banking service is not found served by counter
     */
    List<BankingService> getServicesForCounter(Integer counterId);
}
