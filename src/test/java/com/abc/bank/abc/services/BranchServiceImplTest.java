package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.*;
import com.abc.bank.abc.repositories.BranchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BranchServiceImplTest {

    @InjectMocks
    BranchServiceImpl branchService;

    @Mock
    BranchRepository branchRepository;

    @Mock
    MultiCounterServicesService multiCounterServicesService;

    @Mock
    ServicesSevice servicesSevice;

    @Test
    public void getAllBranches() {
        Branch branch = new Branch();
        branch.setName("hyderabad");
        branch.setId(1);

        Branch branch1 = new Branch();
        branch1.setName("bangalore");
        branch1.setId(2);

        List<Branch> branches = new ArrayList<>();

        branches.add(branch);
        branches.add(branch1);

        when(branchRepository.findAll()).thenReturn(branches);
        List<Branch> fetchedBranches = branchService.getAllBranches();

        assertEquals(2, fetchedBranches.size());
        assertEquals("hyderabad", branches.get(0).getName());
    }

    @Test
    public void getBranch() {
        Branch branch = new Branch();
        branch.setId(1);
        branch.setName("hyderabad");

        Optional<Branch> branchOptional = Optional.of(branch);
        when(branchRepository.findById(1)).thenReturn(branchOptional);

        Branch fetchedBranch = branchService.getBranch(1);
        assertEquals("hyderabad", fetchedBranch.getName());
        assertEquals(1, fetchedBranch.getId());
    }

    @Test
    public void createNewBranch() {
        Branch branch = new Branch();
        branch.setName("hyderabad");
        branch.setId(0);

        Branch branch1 = new Branch();
        branch1.setName("hyderabad");
        branch1.setId(1);
        when(branchRepository.save(branch)).thenReturn(branch1);

        Branch createdBranch = branchService.createNewBranch(branch);

        assertEquals(1, createdBranch.getId());
        assertEquals("hyderabad", createdBranch.getName());
    }

    @Test
    public void getBankingService() {
        Branch branch = new Branch();
        branch.setId(1);
        branch.setName("hyderabad");

        List<BankingService> bankingServices = new ArrayList<>();

        BankingService bankingService = new BankingService();
        bankingService.setId(1);
        bankingService.setName("service 1");

        BankingService bankingService1 = new BankingService();
        bankingService1.setId(2);
        bankingService1.setName("service 2");

        bankingServices.add(bankingService);
        bankingServices.add(bankingService1);

        branch.setBankingServices(bankingServices);

        when(branchRepository.findById(1)).thenReturn(Optional.ofNullable(branch));

        when(servicesSevice.getBankingServiceForBranch(1, 1)).thenReturn(bankingService);

        BankingService fetchedBankingService = branchService.getBankingService(1,1);

        assertEquals(1, fetchedBankingService.getId());
        assertEquals("service 1", fetchedBankingService.getName());
    }

    @Test
    public void addService() {

        Branch branch = new Branch();
        branch.setId(1);
        branch.setName("hyderabad");

        List<BankingService> bankingServices = new ArrayList<>();

        BankingService bankingService = new BankingService();
        bankingService.setId(1);
        bankingService.setName("service 1");

        BankingService bankingService1 = new BankingService();
        bankingService1.setId(2);
        bankingService1.setName("service 2");

        bankingServices.add(bankingService);
        bankingServices.add(bankingService1);

        branch.setBankingServices(bankingServices);

        when(branchRepository.findById(1)).thenReturn(Optional.ofNullable(branch));

        List<BankingService> fetchedBankingServices = branchService.getBankingServices(1);

        assertEquals(2, fetchedBankingServices.size());
        assertEquals(1, fetchedBankingServices.get(0).getId());
        assertEquals("service 1", fetchedBankingServices.get(0).getName());
        assertEquals(2, fetchedBankingServices.get(1).getId());
        assertEquals("service 2", fetchedBankingServices.get(1).getName());

        BankingService bankingService2 = new BankingService();
        bankingService2.setId(3);
        bankingService2.setName("service 3");

        branchService.addBankingService(1, bankingService2);

        fetchedBankingServices = branchService.getBankingServices(1);
        assertEquals(3, fetchedBankingServices.size());

    }

    @Test
    public void getBankingServices() {

        Branch branch = new Branch();
        branch.setId(1);
        branch.setName("hyderabad");

        List<BankingService> bankingServices = new ArrayList<>();

        BankingService bankingService = new BankingService();
        bankingService.setId(1);
        bankingService.setName("service 1");

        BankingService bankingService1 = new BankingService();
        bankingService1.setId(2);
        bankingService1.setName("service 2");

        bankingServices.add(bankingService);
        bankingServices.add(bankingService1);

        branch.setBankingServices(bankingServices);

        when(branchRepository.findById(1)).thenReturn(Optional.ofNullable(branch));

        List<BankingService> fetchedBankingServices = branchService.getBankingServices(1);

        assertEquals(2, fetchedBankingServices.size());
        assertEquals(1, fetchedBankingServices.get(0).getId());
        assertEquals("service 1", fetchedBankingServices.get(0).getName());
        assertEquals(2, fetchedBankingServices.get(1).getId());
        assertEquals("service 2", fetchedBankingServices.get(1).getName());

    }

    @Test
    public void getMultiCounterBankingServices() {

        Branch branch = new Branch();
        branch.setId(1);
        branch.setName("hyderabad");

        List<MultiCounterBankingService> multiCounterBankingServices = new ArrayList<>();

        MultiCounterBankingService multiCounterBankingService = new MultiCounterBankingService();
        multiCounterBankingService.setId(1);
        multiCounterBankingService.setName("multi counter service 1");

        MultiCounterBankingService multiCounterBankingService1 = new MultiCounterBankingService();
        multiCounterBankingService1.setId(2);
        multiCounterBankingService1.setName("multi counter service 2");

        multiCounterBankingServices.add(multiCounterBankingService);
        multiCounterBankingServices.add(multiCounterBankingService1);

        branch.setMultiCounterBankingServices(multiCounterBankingServices);

        when(branchRepository.findById(1)).thenReturn(Optional.ofNullable(branch));

        List<MultiCounterBankingService> fetchedMultiCounterBankingServices = branchService.getMultiCounterBankingServices(1);

        assertEquals(2, fetchedMultiCounterBankingServices.size());
        assertEquals(1, fetchedMultiCounterBankingServices.get(0).getId());
        assertEquals("multi counter service 1", fetchedMultiCounterBankingServices.get(0).getName());
        assertEquals(2, fetchedMultiCounterBankingServices.get(1).getId());
        assertEquals("multi counter service 2", fetchedMultiCounterBankingServices.get(1).getName());
    }

    @Test
    public void getMultiCounterService() {

        Branch branch = new Branch();
        branch.setId(1);
        branch.setName("hyderabad");

        List<MultiCounterBankingService> multiCounterBankingServices = new ArrayList<>();

        MultiCounterBankingService multiCounterBankingService = new MultiCounterBankingService();
        multiCounterBankingService.setId(1);
        multiCounterBankingService.setName("multi counter service 1");

        MultiCounterBankingService multiCounterBankingService1 = new MultiCounterBankingService();
        multiCounterBankingService1.setId(2);
        multiCounterBankingService1.setName("multi counter service 2");

        multiCounterBankingServices.add(multiCounterBankingService);
        multiCounterBankingServices.add(multiCounterBankingService1);

        branch.setMultiCounterBankingServices(multiCounterBankingServices);

        when(branchRepository.findById(1)).thenReturn(Optional.ofNullable(branch));

        when(multiCounterServicesService.getMultiCounterServiceForBranch(1, 1)).thenReturn(multiCounterBankingService);

        MultiCounterBankingService fetchedMultiCounterBankingService = branchService.getMultiCounterBankingService(1,1);

        assertEquals(1, fetchedMultiCounterBankingService.getId());
        assertEquals("multi counter service 1", fetchedMultiCounterBankingService.getName());
    }
}