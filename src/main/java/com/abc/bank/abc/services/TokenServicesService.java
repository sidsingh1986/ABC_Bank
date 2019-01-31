package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.TokenService;
import com.abc.bank.abc.enums.TokenServiceStatus;

public interface TokenServicesService {

    TokenService createTokenService(TokenService tokenService);

    TokenService getTokenService(Integer serviceId);

    TokenService getHighestOrderPendingTokenService(int id);

    TokenService updateTokenServiceStatus(TokenService tokenService, TokenServiceStatus tokenServiceStatus);
}
