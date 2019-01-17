package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.MultiCounterBankingService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MultiCounterServicesService {
    MultiCounterBankingService getMultiCounterService(Integer serviceId);

    MultiCounterBankingService createMultiCounterService(MultiCounterBankingService multiCounterBankingService);

    List<MultiCounterBankingService> getMultiCounterServices();
}
