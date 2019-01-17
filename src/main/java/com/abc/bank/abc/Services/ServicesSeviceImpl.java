package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesSeviceImpl implements ServicesSevice {

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public BankingService getService(Integer serviceId) {
        return serviceRepository.findById(serviceId).get();
    }

    @Override
    public BankingService createService(BankingService bankingService) {
        return serviceRepository.save(bankingService);
    }

    @Override
    public List<BankingService> getServices() {
        return serviceRepository.findAll();
    }
}
