package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.MultiCounterBankingService;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;

import java.util.List;

public interface MultiCounterServicesService {

    /**
     * For getting a particular Multi counter Banking Service
     *
     * @param serviceId service identifier
     * @return Multi counter service with service Id
     * @throws ResourceNotFoundException if the Banking service for the id is not found
     */
    MultiCounterBankingService getMultiCounterService(Integer serviceId);

    /**
     * For creating a Multi Counter Banking Service
     *
     * @param multiCounterBankingService multi counter banking service instance
     * @return created Multi counter banking service instance
     */
    MultiCounterBankingService createMultiCounterService(MultiCounterBankingService multiCounterBankingService);

    /**
     * For getting the list of all Multi counter Banking services in the system
     *
     * @return list of all Multi counter banking services
     */
    List<MultiCounterBankingService> getMultiCounterServices();

    /**
     * For updating a Multi Counter Banking service or creating it if the Multi counter Banking service does not exists
     *
     * @param serviceId
     * @param multiCounterBankingService multi counter banking service to be updated
     */
    void updateMultiCounterService(Integer serviceId, MultiCounterBankingService multiCounterBankingService);

    /**
     * For deleting a particular Multi Counter Banking Service
     *
     * @param serviceId service identifier
     */
    void deleteMultiCounterService(Integer serviceId);

    /**
     * For getting a particular Multi counter Banking Service of a branch
     *
     * @param branchId branch identifier
     * @param serviceId service identifier
     * @return Multi counter service with service Id
     * @throws ResourceNotFoundException if the Banking service for the id is not found
     */
    MultiCounterBankingService getMultiCounterServiceForBranch(Integer branchId, Integer serviceId);
}
