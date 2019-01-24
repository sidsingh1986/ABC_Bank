package com.abc.bank.abc.Services;

import com.abc.bank.abc.DtoModels.BranchDTO;
import com.abc.bank.abc.Enums.CustomerType;
import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.Branch;
import com.abc.bank.abc.Models.Counter;
import com.abc.bank.abc.Models.MultiCounterBankingService;

import java.util.List;

public interface BranchService {
    List<Branch> getAllBranches();

    Branch getBranch(Integer branchId);

    Branch createNewBranch(Branch branch);

    void updateBranch(Branch branch);

    void deleteBranch(Integer branchId);

    BankingService getBankingService(Integer branchId, Integer serviceId);

    Branch addBankingService(Integer branchId, BankingService bankingService);

    List<BankingService> getBankingServices(Integer branchId);

    List<MultiCounterBankingService> getMultiCounterBankingServices(Integer branchId);

    MultiCounterBankingService getMultiCounterBankingService(Integer branchId, Integer serviceId);

    Branch addMultiCounterBankingService(Integer branchId, MultiCounterBankingService multiCounterBankingService);

    List<Counter> getCounters(Integer branchId);

    Counter getCounter(Integer branchId, Integer counterNumber);

    Branch addCounter(Integer branchId, Counter counter);

    List<Counter> getCountersForService(Integer branchId, Integer serviceId, CustomerType customerType);
}
