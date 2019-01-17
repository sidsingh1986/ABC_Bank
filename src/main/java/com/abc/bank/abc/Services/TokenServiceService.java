package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.*;

import java.util.List;

public interface TokenServiceService {

    void completeTokenService(TokenService tokenService);

    void cancelTokenService(TokenService tokenService);

    TokenService getHighestOrderTokenService(Integer tokenId);

    TokenService getPendingTokenServiceBasedOnBankingService(Integer tokenId, BankingService bankingService);

    List<TokenService> generateTokenServices(ServicesPlaceholder servicesPlaceholder);

    void addActionOrComments(TokenService tokenService, String actionOrComments, Employee servedBy);

    void setTokenStatusToProcess(TokenService tokenService);

    TokenService createTokenService(TokenService tokenService);

    void updateTokenService(TokenService tokenService);

    TokenService getTokenService(Integer tokenServiceId);

    List<TokenService> getTokenServices();
}
