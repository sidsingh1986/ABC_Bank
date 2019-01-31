package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.repositories.ServiceRepository;
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
            throw new ResourceNotFoundException( serviceId, "Banking Service not found for id");
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

    @Override
    public void updateService(Integer serviceId, BankingService bankingService) {

        if (!serviceRepository.findById(serviceId).isPresent()) {
            throw new ResourceNotFoundException(serviceId, "The Service you are trying to update is not present");
        }
        bankingService.setId(serviceId);
        serviceRepository.save(bankingService);
    }

    @Override
    public void deleteService(Integer serviceId) {
        Optional<BankingService> optional = serviceRepository.findById(serviceId);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException( serviceId, "Banking Service not found for id");
        }
        serviceRepository.deleteById(serviceId);
    }

    @Override
    public BankingService getServiceForMultiCounterService(Integer multiCounterServiceId, Integer serviceId) {
        return serviceRepository.getService(multiCounterServiceId,serviceId);
    }
}
