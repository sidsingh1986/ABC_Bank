package com.abc.bank.abc.Services;

import com.abc.bank.abc.DataModels.BankingService;

import java.util.List;

public interface ServicesSevice {
    BankingService getService(Integer serviceId);

    BankingService createService(BankingService bankingService);

    List<BankingService> getServices();

    void updateService(BankingService bankingService);

    void deleteService(Integer serviceId);
}
