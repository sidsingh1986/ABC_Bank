package com.abc.bank.abc.Services;

import com.abc.bank.abc.Enums.CustomerType;
import com.abc.bank.abc.DataModels.BankingService;
import com.abc.bank.abc.DataModels.Branch;
import com.abc.bank.abc.DataModels.Counter;
import com.abc.bank.abc.DataModels.MultiCounterBankingService;
import com.abc.bank.abc.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface BranchService {

    /**
     * Gets all the branches defined in the system.
     *
     * @return list of branches
     */
    List<Branch> getAllBranches();

    /**
     * Gets a specific branch by id.
     *
     * @param branchId branch identifier
     * @return branch instance if exists null otherwise
     */
    Branch getBranch(Integer branchId);

    /**
     * Creates a new branch in the system
     *
     * @param branch branch instance
     * @return new branch instance
     */
    Branch createNewBranch(Branch branch);

    /**
     * Updates a branch in the system
     *
     * @param branch branch instance
     */
    void updateBranch(Branch branch);

    /**
     * Deletes a branch in the system
     *
     * @param branchId branch identifier
     */
    void deleteBranch(Integer branchId);

    /**
     * For getting a particular service offered by a branch
     *
     * @param branchId branch identifier
     * @param serviceId service identifier
     * @return banking service
     */
    BankingService getBankingService(Integer branchId, Integer serviceId);

    /**
     * Adds a service to the list of existing services served by a branch
     *
     * @param branchId branch identifier
     * @param bankingService banking service
     * @return branch with updated services
     */
    Branch addBankingService(Integer branchId, BankingService bankingService);

    /**
     * For getting the services offered by a branch
     *
     * @param branchId branch identifier
     * @return list of banking services
     */
    List<BankingService> getBankingServices(Integer branchId);

    /**
     * Gets list of multi counter services offered by a branch
     *
     * @param branchId branch identifier
     * @return list of multi counter services
     */
    List<MultiCounterBankingService> getMultiCounterBankingServices(Integer branchId);

    /**
     * Gets a particular multi counter service offered by a branch
     *
     * @param branchId branch identifier
     * @param serviceId service identifier
     * @return multi counter service
     * @throws ResourceNotFoundException when the service is not served by  branch
     */
    MultiCounterBankingService getMultiCounterBankingService(Integer branchId, Integer serviceId);

    /**
     * Adds a multi counter service to the multi counter services offered by a branch
     *
     * @param branchId branch identifier
     * @param multiCounterBankingService multi counter banking service
     * @return branch with added multi counter service
     * @throws ResourceNotFoundException when branch does not exist
     */
    Branch addMultiCounterBankingService(Integer branchId, MultiCounterBankingService multiCounterBankingService);

    /**
     * Gets the list of counters present in the branch
     *
     * @param branchId branch identifier
     * @return list of counters present in the branch
     * @throws ResourceNotFoundException when branch does not exist or
     * no counter is present in branch
     */
    List<Counter> getCounters(Integer branchId);

    /**
     * Gets a particular counter present in the branch
     *
     * @param branchId branch identifier
     * @param counterNumber counter identifier
     * @return counter with the id passed
     * @throws ResourceNotFoundException when branch does not exist or
     * counter with passed id is not present in branch
     */
    Counter getCounter(Integer branchId, Integer counterNumber);

    /**
     * Adds a counter to the branch
     *
     * @param branchId branch identifier
     * @param counter counter instance
     * @return branch with counter added
     * @throws ResourceNotFoundException when branch does not exist or
     * counter with passed id is not present in branch
     */
    Branch addCounter(Integer branchId, Counter counter);

    /**
     * Gets the counters of branch serving the particular service
     *
     * @param branchId branch identifier
     * @param serviceId service identifer
     * @param customerType type of customer(REGULAR,PRIORITY)
     * @return branch with counter added
     * @throws ResourceNotFoundException when branch does not exist or
     * counter with passed id is not present in branch
     */
    List<Counter> getCountersForService(Integer branchId, Integer serviceId, CustomerType customerType);
}
