package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.MultiCounterBankingService;
import com.abc.bank.abc.Repositories.MultiCounterRepository;
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
public class MultiCounterServicesServiceImplTest {

    @InjectMocks
    MultiCounterServicesServiceImpl multiCounterServicesService;

    @Mock
    MultiCounterRepository multiCounterRepository;

    private MultiCounterBankingService createStubService() {
        MultiCounterBankingService multiCounterBankingService = new MultiCounterBankingService();
        multiCounterBankingService.setName("Multi Counter Service 1");
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

        return multiCounterBankingService;
    }

    @Test
    public void getMultiCounterService() {
        MultiCounterBankingService multiCounterBankingService = createStubService();

        Optional<MultiCounterBankingService> optionalMultiCounterServicesService = Optional.of(multiCounterBankingService);
        when(multiCounterRepository.findById(1)).thenReturn(optionalMultiCounterServicesService);

        MultiCounterBankingService fetchedMultiCounterService = multiCounterServicesService.getMultiCounterService(1);

        assertEquals("Multi Counter Service 1", fetchedMultiCounterService.getName());
    }

    @Test
    public void createMultiCounterService() {
        MultiCounterBankingService multiCounterBankingService = createStubService();

        MultiCounterBankingService returnedMultiCounterService = new MultiCounterBankingService();
        returnedMultiCounterService.setName(multiCounterBankingService.getName());
        returnedMultiCounterService.setBankingServices(multiCounterBankingService.getBankingServices());
        returnedMultiCounterService.setId(1);

        when(multiCounterRepository.save(multiCounterBankingService)).thenReturn(returnedMultiCounterService);

        MultiCounterBankingService fetchedMultiCounterService = multiCounterServicesService.createMultiCounterService(multiCounterBankingService);

        assertEquals("Multi Counter Service 1", fetchedMultiCounterService.getName());
        assertEquals(1, fetchedMultiCounterService.getId());

    }

    @Test
    public void getMultiCounterServices() {
        MultiCounterBankingService multiCounterBankingService = createStubService();
        multiCounterBankingService.setId(1);

        MultiCounterBankingService multiCounterBankingService1 = createStubService();
        multiCounterBankingService1.setId(2);

        List<MultiCounterBankingService> multiCounterBankingServiceList = new ArrayList<>();
        multiCounterBankingServiceList.add(multiCounterBankingService);
        multiCounterBankingServiceList.add(multiCounterBankingService1);

        when(multiCounterRepository.findAll()).thenReturn(multiCounterBankingServiceList);

        List<MultiCounterBankingService> fetchMultiCounterServicesList = multiCounterServicesService.getMultiCounterServices();
        assertEquals(2, fetchMultiCounterServicesList.size());

    }
}