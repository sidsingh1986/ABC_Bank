package com.abc.bank.abc.Services;

import com.abc.bank.abc.Enums.TypeOfService;
import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.Branch;
import com.abc.bank.abc.Models.Counter;
import com.abc.bank.abc.Models.MultiCounterBankingService;
import com.abc.bank.abc.Repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    ServicesSevice servicesSevice;

    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @Override
    public Branch getBranch(Integer branchId) {
        return branchRepository.findById(branchId).get();
    }

    @Override
    public Branch createNewBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public BankingService getService(Integer branchId, Integer serviceId) {
        Branch branch = getBranch(branchId);
        List<BankingService> bankingServices = branch.getBankingServices();

        for(int index = 0; index < bankingServices.size(); index++) {
            if(bankingServices.get(index).getId() == serviceId) {
                return bankingServices.get(index);
            }
        }

        return null;
    }

    @Override
    public Branch addService(Integer branchId, BankingService bankingService) {
        Branch branch = getBranch(branchId);
        branch.getBankingServices().add(bankingService);
        return branchRepository.save(branch);
    }

    @Override
    public List<BankingService> getBankingServices(Integer branchId) {
        Branch branch = getBranch(branchId);
        return branch.getBankingServices();
    }

    @Override
    public List<MultiCounterBankingService> getMultiCounterBankingServices(Integer branchId) {
        Branch branch = getBranch(branchId);
        return branch.getMultiCounterBankingServices();
    }

    @Override
    public MultiCounterBankingService getMultiCounterService(Integer branchId, Integer serviceId) {
        Branch branch = getBranch(branchId);
        List<MultiCounterBankingService> multiCounterBankingServices = branch.getMultiCounterBankingServices();

        for(int index = 0; index < multiCounterBankingServices.size(); index++) {
            if(multiCounterBankingServices.get(index).getId() == serviceId) {
                return multiCounterBankingServices.get(index);
            }
        }
        return  null;
    }

    @Override
    public Branch addMultiCounterService(Integer branchId, MultiCounterBankingService multiCounterBankingService) {
        Branch branch = getBranch(branchId);
        branch.getMultiCounterBankingServices().add(multiCounterBankingService);
        return branchRepository.save(branch);
    }

    @Override
    public List<Counter> getCounters(Integer branchId) {
        return getBranch(branchId).getCounters();
    }

    @Override
    public Counter getCounter(Integer branchId, Integer counterNumber) {
        Branch branch = getBranch(branchId);
        List<Counter> counters = branch.getCounters();

        for(int index = 0; index < counters.size(); index++) {
            if(counters.get(index).getId() == counterNumber) {
                return counters.get(index);
            }
        }
        return  null;
    }

    @Override
    public Branch addCounter(Integer branchId, Counter counter) {
        Branch branch = getBranch(branchId);
        branch.getCounters().add(counter);
        return branchRepository.save(branch);
    }

    @Override
    public List<Counter> getCountersForService(Integer branchId, Integer serviceId, TypeOfService typeOfService) {
        BankingService bankingService = servicesSevice.getService(serviceId);
        Branch branch = branchRepository.findById(branchId).get();
        List<Counter> counters = branch.getCounters();
        List<Counter> servicesCounter = new ArrayList<>();
        for (int index = 0; index < counters.size(); index++) {
            Counter counter = counters.get(index);
            if (counter.getServicesOffered().contains(bankingService) && counter.getTypeOfService().equals(typeOfService))
                servicesCounter.add(counter);
        }
        return servicesCounter;
    }
}
