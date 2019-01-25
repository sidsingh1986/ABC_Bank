package com.abc.bank.abc.Services;

import com.abc.bank.abc.DataModels.MultiCounterBankingService;
import com.abc.bank.abc.Repositories.MultiCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultiCounterServicesServiceImpl implements MultiCounterServicesService {

    @Autowired
    MultiCounterRepository multiCounterRepository;

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
    public void updateMultiCounterService(MultiCounterBankingService multiCounterBankingService) {
        multiCounterRepository.save(multiCounterBankingService);
    }

    @Override
    public void deleteMultiCounterService(Integer serviceId) {
        multiCounterRepository.deleteById(serviceId);
    }
}
