package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesSeviceImpl implements ServicesSevice {

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public BankingService getService(Integer serviceId) {
        Optional<BankingService> optional = serviceRepository.findById(serviceId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Banking Service not found for id " + serviceId);
        }
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
