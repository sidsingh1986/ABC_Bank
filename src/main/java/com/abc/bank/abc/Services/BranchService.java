package com.abc.bank.abc.Services;

import com.abc.bank.abc.Enums.TypeOfService;
import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.Branch;
import com.abc.bank.abc.Models.Counter;
import com.abc.bank.abc.Models.MultiCounterBankingService;

import java.util.List;

public interface BranchService {
    List<Branch> getAllBranches();

    Branch getBranch(Integer branchId);

    Branch createNewBranch(Branch branch);

    BankingService getService(Integer branchId, Integer serviceId);

    Branch addService(Integer branchId, BankingService bankingService);

    List<BankingService> getBankingServices(Integer branchId);

    List<MultiCounterBankingService> getMultiCounterBankingServices(Integer branchId);

    MultiCounterBankingService getMultiCounterService(Integer branchId, Integer serviceId);

    Branch addMultiCounterService(Integer branchId, MultiCounterBankingService multiCounterBankingService);

    List<Counter> getCounters(Integer branchId);

    Counter getCounter(Integer branchId, Integer counterNumber);

    Branch addCounter(Integer branchId, Counter counter);

    List<Counter> getCountersForService(Integer branchId, Integer serviceId, TypeOfService typeOfService);
}
