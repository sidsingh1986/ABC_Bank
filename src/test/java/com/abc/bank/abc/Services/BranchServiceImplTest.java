package com.abc.bank.abc.Services;

import com.abc.bank.abc.Enums.TokenServiceStatus;
import com.abc.bank.abc.Enums.TokenStatus;
import com.abc.bank.abc.Enums.TypeOfService;
import com.abc.bank.abc.Models.*;
import com.abc.bank.abc.Repositories.BranchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
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
    ServicesSevice servicesSevice;

    @Test
    public void getAllBranches() {
        Branch branch = stubCode();
        when(branchRepository.findAll()).thenReturn(Arrays.asList(branch));
        List<Branch> branches = branchService.getAllBranches();

        assertEquals(1, branches.size());
        assertEquals("hyderabad", branches.get(0).getName());
    }

    @Test
    public void getBranch() {
        Branch branch = stubCode();
        Optional<Branch> branchOptional = Optional.of(branch);
        when(branchRepository.findById(1)).thenReturn(branchOptional);

        Branch fetchedBranch = branchService.getBranch(1);
        assertEquals("hyderabad", fetchedBranch.getName());
        assertEquals(4, fetchedBranch.getCounters().size());
    }

    @Test
    public void createNewBranch() {
    }

    @Test
    public void getService() {
    }

    @Test
    public void addService() {
    }

    @Test
    public void getBankingServices() {
    }

    @Test
    public void getMultiCounterBankingServices() {
    }

    @Test
    public void getMultiCounterService() {
    }

    @Test
    public void addMultiCounterService() {
    }

    @Test
    public void getCounters() {
    }

    @Test
    public void getCounter() {
    }

    @Test
    public void addCounter() {
    }

    @Test
    public void getCountersForService() {
    }

    private Branch stubCode() {
        Branch branch = new Branch();
        branch.setId(1);

        Address address = new Address();
        address.setFirstLine("first_line_of_address");
        address.setSecondLine("Second_line_of_address");
        address.setThirdLine("Third_line_of_address");
        address.setId(1);
        address.setCity("hyderabad");
        address.setPincode("500034");

        branch.setAddress(address);

        Bank bank = new Bank();
        bank.setName("ABC Bank");

        branch.setBank(bank);

        MultiCounterBankingService multiCounterBankingService = new MultiCounterBankingService();
        multiCounterBankingService.setName("Multi Counter Service 1");
        multiCounterBankingService.setId(1);
        List<BankingService> bankingServices = new ArrayList<>();

        BankingService bankingService = new BankingService();
        bankingService.setName("Banking service 1");
        bankingService.setId(1);

        BankingService bankingService1 = new BankingService();
        bankingService1.setName("Banking service 2");
        bankingService1.setId(2);

        bankingServices.add(bankingService);
        bankingServices.add(bankingService1);
        multiCounterBankingService.setBankingServices(bankingServices);

        MultiCounterBankingService multiCounterBankingService1 = new MultiCounterBankingService();
        multiCounterBankingService1.setName("Multi Counter Service 2");
        multiCounterBankingService1.setId(2);

        List<BankingService> bankingServices1 = new ArrayList<>();

        BankingService bankingService2 = new BankingService();
        bankingService2.setName("Banking service 3");
        bankingService2.setId(3);

        BankingService bankingService3 = new BankingService();
        bankingService3.setName("Banking service 4");
        bankingService3.setId(4);

        bankingServices1.add(bankingService2);
        bankingServices1.add(bankingService3);
        multiCounterBankingService1.setBankingServices(bankingServices1);

        Counter counter = new Counter();
        counter.setId(1);
        counter.setName("counter 1");
        counter.setTypeOfService(TypeOfService.REGULAR);
        counter.setServicesOffered(bankingServices);

        Counter counter1 = new Counter();
        counter1.setId(2);
        counter1.setName("counter 2");
        counter1.setTypeOfService(TypeOfService.REGULAR);
        counter1.setServicesOffered(bankingServices1);

        Counter counter2 = new Counter();
        counter2.setId(3);
        counter2.setName("counter 3");
        counter2.setTypeOfService(TypeOfService.PREMIUM);
        counter2.setServicesOffered(bankingServices);

        Counter counter3 = new Counter();
        counter3.setId(4);
        counter3.setName("counter 4");
        counter3.setTypeOfService(TypeOfService.PREMIUM);
        counter3.setServicesOffered(bankingServices1);

        Customer customer = new Customer();
        customer.setId(1);
        customer.setBranch(branch);
        customer.setAddress(address);
        customer.setTypeOfService(TypeOfService.REGULAR);
        customer.setName("Customer 1");
        customer.setPhoneNumber("1234567");

        Customer customer1 = new Customer();
        customer1.setId(2);
        customer1.setBranch(branch);
        customer1.setAddress(address);
        customer1.setTypeOfService(TypeOfService.REGULAR);
        customer1.setName("Customer 2");
        customer1.setPhoneNumber("1234567");

        Customer customer2 = new Customer();
        customer2.setId(3);
        customer2.setBranch(branch);
        customer2.setAddress(address);
        customer2.setTypeOfService(TypeOfService.PREMIUM);
        customer2.setName("Customer 3");
        customer2.setPhoneNumber("9876543");

        Customer customer3 = new Customer();
        customer3.setId(4);
        customer3.setBranch(branch);
        customer3.setAddress(address);
        customer3.setTypeOfService(TypeOfService.PREMIUM);
        customer3.setName("Customer 4");
        customer3.setPhoneNumber("9876543");


        Token token1 = new Token();
        token1.setCustomer(customer);
        token1.setStatus(TokenStatus.ISSUED);
        token1.setId(1);

        TokenService tokenService1 = new TokenService();
        tokenService1.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        tokenService1.setRequestOrder(1);
        tokenService1.setService(bankingService);
        tokenService1.setCounter(counter);

        TokenService tokenService2 = new TokenService();
        tokenService2.setStatus(TokenServiceStatus.QUEUED);
        tokenService2.setRequestOrder(2);
        tokenService2.setService(bankingService1);

        List<TokenService> tokenServices1 = new ArrayList<>();
        tokenServices1.add(tokenService1);
        tokenServices1.add(tokenService2);

        token1.setTokenServices(tokenServices1);


        Token token2 = new Token();
        token2.setCustomer(customer1);
        token2.setStatus(TokenStatus.ISSUED);
        token2.setId(2);

        TokenService tokenService3 = new TokenService();
        tokenService3.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        tokenService3.setRequestOrder(1);
        tokenService3.setService(bankingService2);
        tokenService3.setCounter(counter1);

        TokenService tokenService4 = new TokenService();
        tokenService4.setStatus(TokenServiceStatus.QUEUED);
        tokenService4.setRequestOrder(2);
        tokenService4.setService(bankingService3);

        List<TokenService> tokenServices2 = new ArrayList<>();
        tokenServices2.add(tokenService3);
        tokenServices2.add(tokenService4);

        token2.setTokenServices(tokenServices2);


        Token token3 = new Token();
        token3.setCustomer(customer2);
        token3.setStatus(TokenStatus.ISSUED);
        token3.setId(3);

        TokenService tokenService5 = new TokenService();
        tokenService5.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        tokenService5.setRequestOrder(1);
        tokenService5.setService(bankingService);
        tokenService5.setCounter(counter2);

        TokenService tokenService6 = new TokenService();
        tokenService6.setStatus(TokenServiceStatus.QUEUED);
        tokenService6.setRequestOrder(2);
        tokenService6.setService(bankingService1);

        List<TokenService> tokenServices3 = new ArrayList<>();
        tokenServices3.add(tokenService5);
        tokenServices3.add(tokenService6);

        token3.setTokenServices(tokenServices3);


        Token token4 = new Token();
        token4.setCustomer(customer3);
        token4.setStatus(TokenStatus.ISSUED);
        token4.setId(4);

        TokenService tokenService7 = new TokenService();
        tokenService7.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        tokenService7.setRequestOrder(1);
        tokenService7.setService(bankingService2);
        tokenService7.setCounter(counter3);

        TokenService tokenService8 = new TokenService();
        tokenService8.setStatus(TokenServiceStatus.QUEUED);
        tokenService8.setRequestOrder(2);
        tokenService8.setService(bankingService3);

        List<TokenService> tokenServices4 = new ArrayList<>();
        tokenServices4.add(tokenService7);
        tokenServices4.add(tokenService8);

        token4.setTokenServices(tokenServices4);

        counter.setTokens(Arrays.asList(token1));
        counter1.setTokens(Arrays.asList(token2));
        counter2.setTokens(Arrays.asList(token3));
        counter3.setTokens(Arrays.asList(token4));

        List<Counter> counters = new ArrayList<>();
        counters.add(counter);
        counters.add(counter1);
        counters.add(counter2);
        counters.add(counter3);

        branch.setCounters(counters);
        branch.setName("hyderabad");

        List<BankingService> completeBankingServices = new ArrayList<>();
        completeBankingServices.addAll(bankingServices);
        completeBankingServices.addAll(bankingServices1);
        branch.setBankingServices(completeBankingServices);

        List<MultiCounterBankingService> completeMultiCounterServices = new ArrayList<>();
        completeMultiCounterServices.add(multiCounterBankingService);
        completeMultiCounterServices.add(multiCounterBankingService1);

        branch.setMultiCounterBankingServices(completeMultiCounterServices);

        int i =1;

        return branch;

    }
}