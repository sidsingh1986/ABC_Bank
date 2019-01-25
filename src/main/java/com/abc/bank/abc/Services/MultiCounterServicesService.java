package com.abc.bank.abc.Services;

import com.abc.bank.abc.DataModels.MultiCounterBankingService;

import java.util.List;

public interface MultiCounterServicesService {
    MultiCounterBankingService getMultiCounterService(Integer serviceId);

    MultiCounterBankingService createMultiCounterService(MultiCounterBankingService multiCounterBankingService);

    List<MultiCounterBankingService> getMultiCounterServices();

    void updateMultiCounterService(MultiCounterBankingService multiCounterBankingService);

    void deleteMultiCounterService(Integer serviceId);
}
