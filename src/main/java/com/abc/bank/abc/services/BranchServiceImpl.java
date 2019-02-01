package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.*;
import com.abc.bank.abc.enums.CustomerType;
import com.abc.bank.abc.exceptions.IllegalInputException;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.repositories.BranchRepository;
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

    @Autowired
    MultiCounterServicesService multiCounterServicesService;

    @Autowired
    CounterService counterService;

    @Override
    public List<Branch> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();
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
    public Branch updateBranch(Branch branch, Integer branchId) {

        Optional<Branch> branchOptional = branchRepository.findById(branchId);
        if (!branchOptional.isPresent()) {
            throw new IllegalInputException("the branch you are trying to update does not exist");
        }

        if (branch == null)
            throw new IllegalArgumentException("The branch parameter can't be null");
        branch.setId(branchId);
        return branchRepository.save(branch);
    }

    @Override
    public void deleteBranch(Integer branchId) {
        Optional<Branch> branchOptional = branchRepository.findById(branchId);
        if (!branchOptional.isPresent()) {
            throw new ResourceNotFoundException(branchId, "the branch you are trying to delete does not exist");
        }
        branchRepository.deleteById(branchId);
    }

    @Override
    public BankingService getBankingService(Integer branchId, Integer serviceId) {
        BankingService bankingService = servicesSevice.getBankingServiceForBranch(branchId, serviceId);
        if (bankingService == null)
        throw new ResourceNotFoundException(serviceId, "The requested service is not found for branch");

        return bankingService;
    }

    @Override
    public Branch addBankingService(Integer branchId, BankingService bankingService) {
        Branch branch = getBranch(branchId);
        if (!branch.addBankingService(bankingService)) {
            throw new IllegalInputException("This banking service failed to be added to branch");
        }
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
        MultiCounterBankingService multiCounterBankingService = multiCounterServicesService.getMultiCounterServiceForBranch(branchId, serviceId);

        if (multiCounterBankingService == null)
            throw new ResourceNotFoundException(serviceId, "The requested multi counter service is not found for branch");

        return multiCounterBankingService;
    }

    @Override
    public Branch addMultiCounterBankingService(Integer branchId, MultiCounterBankingService multiCounterBankingService) {
        Branch branch = getBranch(branchId);
        branch.addMultiCounterBankingService(multiCounterBankingService);
        return branchRepository.save(branch);
    }

    @Override
    public List<Counter> getCounters(Integer branchId) {
        return getBranch(branchId).getCounters();
    }

    @Override
    public Counter getCounter(Integer branchId, Integer counterNumber) {
        Counter counter = counterService.getCounterForBranch(branchId, counterNumber);

        if (counter == null)
            throw new ResourceNotFoundException(counterNumber, "The requested counter is not found in branch");

        return counter;
    }

    @Override
    public void partialUpdate(Integer branchId, List<String> changedFields, Branch updatedBranch) {

        Branch branch = getBranch(branchId);

        for (int index = 0; index < changedFields.size(); index++) {
            updateBasedOnField(branch, changedFields.get(index), updatedBranch);
        }

        branchRepository.save(branch);
    }

    private void updateBasedOnField(Branch branch, String field, Branch updatedBranch) {


        switch (field) {

            case  "address" :
                branch.setAddress(updatedBranch.getAddress());
                break;

            case "name" :
                branch.setName(updatedBranch.getName());
                break;

            case "branchServices" :
                branch.setBankingServices(updatedBranch.getBankingServices());
                break;

            case "branchMultiCounterServices" :
                branch.setMultiCounterBankingServices(updatedBranch.getMultiCounterBankingServices());
        }

    }
}
