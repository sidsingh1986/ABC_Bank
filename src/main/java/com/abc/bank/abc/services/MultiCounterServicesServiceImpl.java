package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.MultiCounterBankingService;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.repositories.MultiCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultiCounterServicesServiceImpl implements MultiCounterServicesService {

    @Autowired
    MultiCounterRepository multiCounterRepository;

    @Autowired
    ServicesSevice servicesSevice;

    @Override
    public MultiCounterBankingService getMultiCounterService(Integer serviceId) {
        Optional<MultiCounterBankingService> optional = multiCounterRepository.findById(serviceId);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Multi counter service not found for id " + serviceId);
        }
    }

    @Override
    public MultiCounterBankingService createMultiCounterService(MultiCounterBankingService multiCounterBankingService) {
        return multiCounterRepository.save(multiCounterBankingService);
    }

    @Override
    public List<MultiCounterBankingService> getMultiCounterServices() {
        return multiCounterRepository.findAll();
    }

    @Override
    public MultiCounterBankingService updateMultiCounterService(Integer serviceId, MultiCounterBankingService multiCounterBankingService) {

        if (!multiCounterRepository.findById(serviceId).isPresent()) {
            throw new ResourceNotFoundException(serviceId, "The multi counter service you are trying to update is not found");
        }
        multiCounterBankingService.setId(serviceId);
        return multiCounterRepository.save(multiCounterBankingService);
    }

    @Override
    public void deleteMultiCounterService(Integer serviceId) {
        multiCounterRepository.deleteById(serviceId);
    }

    @Override
    public MultiCounterBankingService getMultiCounterServiceForBranch(Integer branchId, Integer serviceId) {
        return multiCounterRepository.getMultiCounterBankingServiceforBranch(branchId, serviceId);
    }
}
