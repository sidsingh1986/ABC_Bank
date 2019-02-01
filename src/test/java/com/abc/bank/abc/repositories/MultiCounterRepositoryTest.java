package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.*;
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
public class MultiCounterRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    MultiCounterRepository multiCounterRepository;

    @Test
    public void getMultiCounterBankingServiceforBranch() {
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

        MultiCounterBankingService fetchedMultiCounterBankingService = multiCounterRepository.getMultiCounterBankingServiceforBranch(createdBranch.getId(),
                createdMultiCounterBankingService.getId());

        assertEquals("Multi Counter Banking Service 1", fetchedMultiCounterBankingService.getName());
        assertEquals(2, fetchedMultiCounterBankingService.getBankingServices().size());
        assertEquals("Service 1", fetchedMultiCounterBankingService.getBankingServices().get(0).getName());

        MultiCounterBankingService fetchedNonExistentMultiCounterBankingService = multiCounterRepository.getMultiCounterBankingServiceforBranch(createdBranch.getId(),
                0);

        assertNull(fetchedNonExistentMultiCounterBankingService);
    }
}