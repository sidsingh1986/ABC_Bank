package com.abc.bank.abc.Services;

import com.abc.bank.abc.DataModels.BankingService;
import com.abc.bank.abc.Exceptions.ResourceNotFoundException;

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
     * @param bankingService banking service to be updated
     */
    void updateService(BankingService bankingService);

    /**
     * For deleting a particular Banking Service
     *
     * @param serviceId service identifier
     */
    void deleteService(Integer serviceId);
}
