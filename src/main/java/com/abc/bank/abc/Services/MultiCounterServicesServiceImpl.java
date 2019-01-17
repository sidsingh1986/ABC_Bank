package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.MultiCounterBankingService;
import com.abc.bank.abc.Repositories.MultiCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultiCounterServicesServiceImpl implements MultiCounterServicesService {

    @Autowired
    MultiCounterRepository multiCounterRepository;

    @Override
    public MultiCounterBankingService getMultiCounterService(Integer serviceId) {
        return multiCounterRepository.findById(serviceId).get();
    }

    @Override
    public MultiCounterBankingService createMultiCounterService(MultiCounterBankingService multiCounterBankingService) {
        return multiCounterRepository.save(multiCounterBankingService);
    }

    @Override
    public List<MultiCounterBankingService> getMultiCounterServices() {
        return multiCounterRepository.findAll();
    }
}
