package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.BankingService;

import java.util.List;

public interface ServicesSevice {
    BankingService getService(Integer serviceId);

    BankingService createService(BankingService bankingService);

    List<BankingService> getServices();
}
