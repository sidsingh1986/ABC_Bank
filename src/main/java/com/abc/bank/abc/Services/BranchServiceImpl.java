package com.abc.bank.abc.Services;

import com.abc.bank.abc.Enums.CustomerType;
import com.abc.bank.abc.Exceptions.ResourceNotFoundException;
import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.Branch;
import com.abc.bank.abc.Models.Counter;
import com.abc.bank.abc.Models.MultiCounterBankingService;
import com.abc.bank.abc.Repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("BranchService")
public class BranchServiceImpl implements BranchService {

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    ServicesSevice servicesSevice;

    @Override
    public List<Branch> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();
        if (branches == null)
            throw new NullPointerException("branches list cannot be null");

        return branches;
    }

    @Override
    public Branch getBranch(Integer branchId) {
        Optional<Branch> branchOptional = branchRepository.findById(branchId);
        if (branchOptional.isPresent()) {
            return branchOptional.get();
        } else {
            throw new ResourceNotFoundException(branchId, "branch not found");
        }

    }

    @Override
    public Branch createNewBranch(Branch branch) {
        if (branch == null)
            throw new IllegalArgumentException("The branch parameter passed can't be null");
        return branchRepository.save(branch);
    }

    @Override
    public void updateBranch(Branch branch) {
        if (branch == null)
            throw new IllegalArgumentException("The branch parameter can't be null");
        branchRepository.save(branch);
    }

    @Override
    public void deleteBranch(Integer branchId) {
        branchRepository.deleteById(branchId);
    }

    @Override
    public BankingService getBankingService(Integer branchId, Integer serviceId) {
        Branch branch = getBranch(branchId);
        List<BankingService> bankingServices = branch.getBankingServices();

        if (bankingServices != null) {

            for (int index = 0; index < bankingServices.size(); index++) {
                if (bankingServices.get(index).getId() == serviceId) {
                    return bankingServices.get(index);
                }
            }
        }
        throw new ResourceNotFoundException(serviceId, "The requested service is not found for branch");
    }

    @Override
    public Branch addBankingService(Integer branchId, BankingService bankingService) {
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
    public MultiCounterBankingService getMultiCounterBankingService(Integer branchId, Integer serviceId) {
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
    public Branch addMultiCounterBankingService(Integer branchId, MultiCounterBankingService multiCounterBankingService) {
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
        throw new ResourceNotFoundException(counterNumber, "Counter not found for branch with id " + branchId);
    }

    @Override
    public Branch addCounter(Integer branchId, Counter counter) {
        Branch branch = getBranch(branchId);
        branch.getCounters().add(counter);
        return branchRepository.save(branch);
    }

    @Override
    public List<Counter> getCountersForService(Integer branchId, Integer serviceId, CustomerType customerType) {
        BankingService bankingService = servicesSevice.getService(serviceId);
        Branch branch = branchRepository.findById(branchId).get();
        List<Counter> counters = branch.getCounters();
        List<Counter> servicesCounter = new ArrayList<>();
        for (int index = 0; index < counters.size(); index++) {
            Counter counter = counters.get(index);
            if (counter.getServicesOffered().contains(bankingService) && counter.getCustomerType().equals(customerType))
                servicesCounter.add(counter);
        }
        return servicesCounter;
    }
}
