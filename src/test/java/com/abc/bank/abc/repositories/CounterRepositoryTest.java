package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.*;
import com.abc.bank.abc.enums.CustomerType;
import com.abc.bank.abc.enums.ServiceProcessingType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CounterRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CounterRepository counterRepository;

    @Test
    public void getAllCounters() {

        List<Counter> counters = counterRepository.findAll();
        assertEquals(0, counters.size());
    }

    @Test
    public void getCountersForService() {
        Bank bank = new Bank();
        bank.setName("ABC Bank");
        Bank createdBank = entityManager.persist(bank);

        Address address = new Address();
        address.setCity("hyderabad");
        address.setFirstLine("line 1");
        address.setSecondLine("line 2");
        address.setThirdLine("line 3");
        address.setPincode("123456");

        Address createdAddress = entityManager.persist(address);

        BankingService bankingService = new BankingService();
        bankingService.setName("Service 1");
        bankingService.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);

        BankingService createdBankingService = entityManager.persist(bankingService);
        BankingService bankingService2 = new BankingService();
        bankingService2.setName("Service 2");
        bankingService2.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);

        BankingService createdBankingService2 = entityManager.persist(bankingService2);

        BankingService bankingService3 = new BankingService();
        bankingService3.setName("Service 3");
        bankingService3.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);

        BankingService createdBankingService3 = entityManager.persist(bankingService3);

        List<BankingService> bankingServices = new ArrayList<>();
        bankingServices.add(createdBankingService);
        bankingServices.add(createdBankingService2);

        MultiCounterBankingService multiCounterBankingService = new MultiCounterBankingService();
        multiCounterBankingService.setName("Multi Counter Banking Service 1");
        multiCounterBankingService.setBankingServices(bankingServices);
        multiCounterBankingService.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);

        MultiCounterBankingService createdMultiCounterBankingService = entityManager.persist(multiCounterBankingService);

        MultiCounterBankingService multiCounterBankingService1 = new MultiCounterBankingService();
        multiCounterBankingService1.setName("Multi Counter Banking Service 2");
        multiCounterBankingService1.setBankingServices(Arrays.asList(createdBankingService2, createdBankingService3));
        multiCounterBankingService1.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);

        MultiCounterBankingService createdMultiCounterBankingService1 = entityManager.persist(multiCounterBankingService1);

        List<MultiCounterBankingService> multiCounterBankingServices = Arrays.asList(createdMultiCounterBankingService,createdMultiCounterBankingService1);

        Branch branch = new Branch();
        branch.setName("hyderabad");
        branch.setBank(createdBank);
        branch.setAddress(createdAddress);
        branch.setBankingServices(bankingServices);
        branch.setMultiCounterBankingServices(multiCounterBankingServices);
        Branch createdBranch = entityManager.persist(branch);

        Counter counter = new Counter();
        counter.setCustomerType(CustomerType.REGULAR);
        counter.setName("Counter 1");
        counter.setBranch(createdBranch);
        counter.setServicesOffered(Arrays.asList(createdBankingService, createdBankingService2));
        counter.setDisplayNumber(1);
        Counter createdCounter = entityManager.persist(counter);

        Counter counter1 = new Counter();
        counter1.setCustomerType(CustomerType.REGULAR);
        counter1.setName("Counter 2");
        counter1.setBranch(createdBranch);
        counter1.setServicesOffered(Arrays.asList(createdBankingService2, createdBankingService3));
        counter1.setDisplayNumber(2);
        Counter createdCounter1 = entityManager.persist(counter1);

        Counter counter2 = new Counter();
        counter2.setCustomerType(CustomerType.PREMIUM);
        counter2.setName("Counter 3");
        counter2.setBranch(createdBranch);
        counter2.setServicesOffered(Arrays.asList(createdBankingService, createdBankingService2));
        counter2.setDisplayNumber(3);
        Counter createdCounter2 = entityManager.persist(counter2);

        Counter counter3 = new Counter();
        counter3.setCustomerType(CustomerType.PREMIUM);
        counter3.setName("Counter 4");
        counter3.setBranch(createdBranch);
        counter3.setServicesOffered(Arrays.asList(createdBankingService2, createdBankingService3));
        counter3.setDisplayNumber(4);
        Counter createdCounter3 = entityManager.persist(counter3);

        List<Counter> counters = counterRepository.getCountersForService(createdBranch.getId(), createdBankingService2.getId(), CustomerType.REGULAR.toString());

        List<Counter> countersList = counterRepository.getCountersForService(createdBranch.getId(), createdBankingService3.getId(), CustomerType.REGULAR.toString());
        assertEquals(2, counters.size());
        assertEquals(1, countersList.size());

        List<Counter> premiumCounterforService1 = counterRepository.getCountersForService(createdBranch.getId(), createdBankingService.getId(), CustomerType.PREMIUM.toString());
        assertEquals(1, premiumCounterforService1.size());
    }

    @Test
    public void getCounterForBranch() {

        Bank bank = new Bank();
        bank.setName("ABC Bank");
        Bank createdBank = entityManager.persist(bank);

        Address address = new Address();
        address.setCity("hyderabad");
        address.setFirstLine("line 1");
        address.setSecondLine("line 2");
        address.setThirdLine("line 3");
        address.setPincode("123456");

        Address createdAddress = entityManager.persist(address);

        BankingService bankingService = new BankingService();
        bankingService.setName("Service 1");
        bankingService.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);

        BankingService createdBankingService = entityManager.persist(bankingService);
        BankingService bankingService2 = new BankingService();
        bankingService2.setName("Service 2");
        bankingService2.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);

        BankingService createdBankingService2 = entityManager.persist(bankingService2);

        BankingService bankingService3 = new BankingService();
        bankingService3.setName("Service 3");
        bankingService3.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);

        BankingService createdBankingService3 = entityManager.persist(bankingService3);

        List<BankingService> bankingServices = new ArrayList<>();
        bankingServices.add(createdBankingService);
        bankingServices.add(createdBankingService2);

        MultiCounterBankingService multiCounterBankingService = new MultiCounterBankingService();
        multiCounterBankingService.setName("Multi Counter Banking Service 1");
        multiCounterBankingService.setBankingServices(bankingServices);
        multiCounterBankingService.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);

        MultiCounterBankingService createdMultiCounterBankingService = entityManager.persist(multiCounterBankingService);

        MultiCounterBankingService multiCounterBankingService1 = new MultiCounterBankingService();
        multiCounterBankingService1.setName("Multi Counter Banking Service 2");
        multiCounterBankingService1.setBankingServices(Arrays.asList(createdBankingService2, createdBankingService3));
        multiCounterBankingService1.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);

        MultiCounterBankingService createdMultiCounterBankingService1 = entityManager.persist(multiCounterBankingService1);

        List<MultiCounterBankingService> multiCounterBankingServices = Arrays.asList(createdMultiCounterBankingService,createdMultiCounterBankingService1);

        Branch branch = new Branch();
        branch.setName("hyderabad");
        branch.setBank(createdBank);
        branch.setAddress(createdAddress);
        branch.setBankingServices(bankingServices);
        branch.setMultiCounterBankingServices(multiCounterBankingServices);
        Branch createdBranch = entityManager.persist(branch);

        Counter counter = new Counter();
        counter.setCustomerType(CustomerType.REGULAR);
        counter.setName("Counter 1");
        counter.setBranch(createdBranch);
        counter.setServicesOffered(Arrays.asList(createdBankingService, createdBankingService2));
        counter.setDisplayNumber(1);
        Counter createdCounter = entityManager.persist(counter);

        Counter counter1 = new Counter();
        counter1.setCustomerType(CustomerType.REGULAR);
        counter1.setName("Counter 2");
        counter1.setBranch(createdBranch);
        counter1.setServicesOffered(Arrays.asList(createdBankingService2, createdBankingService3));
        counter1.setDisplayNumber(2);
        Counter createdCounter1 = entityManager.persist(counter1);

        Counter counter2 = new Counter();
        counter2.setCustomerType(CustomerType.PREMIUM);
        counter2.setName("Counter 3");
        counter2.setBranch(createdBranch);
        counter2.setServicesOffered(Arrays.asList(createdBankingService, createdBankingService2));
        counter2.setDisplayNumber(3);
        Counter createdCounter2 = entityManager.persist(counter2);

        Counter counter3 = new Counter();
        counter3.setCustomerType(CustomerType.PREMIUM);
        counter3.setName("Counter 4");
        counter3.setBranch(createdBranch);
        counter3.setServicesOffered(Arrays.asList(createdBankingService2, createdBankingService3));
        counter3.setDisplayNumber(4);
        Counter createdCounter3 = entityManager.persist(counter3);

        Counter counterForBranch = counterRepository.getCounterForBranch(createdBranch.getId(), createdCounter.getId());

        assertEquals(createdCounter.getId(), counterForBranch.getId());
        assertEquals("Counter 1", counterForBranch.getName());

        Counter counterForBranchNonExistent = counterRepository.getCounterForBranch(createdBranch.getId(), 1);
        assertNull(counterForBranchNonExistent);
    }
}