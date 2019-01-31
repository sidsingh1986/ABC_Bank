package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.TokenMultiCounterService;
import com.abc.bank.abc.enums.TokenServiceStatus;

public interface TokenMultiCounterServicesService {

    TokenMultiCounterService createTokenMultiCounterService(TokenMultiCounterService tokenMultiCounterService);

    TokenMultiCounterService getTokenMultiCounterService(Integer serviceId);

    TokenMultiCounterService getHighestOrderPendingMultiCounterService(int id);

    TokenMultiCounterService updateTokenMultiCounterServiceStatus(TokenMultiCounterService tokenMultiCounterService, TokenServiceStatus tokenServiceStatus);
}
