package com.abc.bank.abc.Services;

import com.abc.bank.abc.DataModels.BankingService;
import com.abc.bank.abc.Repositories.ServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServicesSeviceImplTest {

    @InjectMocks
    ServicesSeviceImpl servicesSevice;

    @Mock
    ServiceRepository serviceRepository;

    @Test
    public void getService() {
        Integer branchId = 1;
        BankingService bankingService = new BankingService();
        bankingService.setName("Banking service 1");
        Optional<BankingService> optionalBankingService = Optional.of(bankingService);
        when(serviceRepository.findById(branchId)).thenReturn(optionalBankingService);
        BankingService fetchedService = servicesSevice.getService(branchId);
        assertEquals( "Banking service 1", fetchedService.getName());
    }

    @Test
    public void createService() {
        BankingService bankingService = new BankingService();
        bankingService.setName("Banking service 1");

        BankingService outputBankingService = new BankingService();
        outputBankingService.setName(bankingService.getName());
        outputBankingService.setId(1);

        when(serviceRepository.save(bankingService)).thenReturn(outputBankingService);

        BankingService fetchedBankingService = servicesSevice.createService(bankingService);

        assertEquals(1, fetchedBankingService.getId());
        assertEquals("Banking service 1", fetchedBankingService.getName());
    }

    @Test
    public void getServices() {
        List<BankingService> bankingServiceList = new ArrayList<>();

        BankingService bankingService = new BankingService();
        bankingService.setName("Banking service 1");
        bankingService.setId(1);

        BankingService bankingService1 = new BankingService();
        bankingService1.setName("Banking service 2");
        bankingService1.setId(2);

        bankingServiceList.add(bankingService);
        bankingServiceList.add(bankingService1);
        when(serviceRepository.findAll()).thenReturn(bankingServiceList);

       List<BankingService> fetchedServicesList =  servicesSevice.getServices();

       assertEquals(2, fetchedServicesList.size());
    }
}