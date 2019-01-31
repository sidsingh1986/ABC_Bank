package com.abc.bank.abc.viewmodels;

import com.abc.bank.abc.enums.TokenServiceStatus;
import lombok.Data;


public class ServicesPlaceholder {

    BankingServiceModel service;

    int orderOfService;

    TokenServiceStatus tokenServiceStatus;

    public BankingServiceModel getService() {
        return service;
    }

    public void setService(BankingServiceModel service) {
        if (service == null) {
            throw new NullPointerException("The service should not be set to Null");
        }
        this.service = service;
    }

    public int getOrderOfService() {
        return orderOfService;
    }

    public void setOrderOfService(int orderOfService) {
        this.orderOfService = orderOfService;
    }

    public TokenServiceStatus getTokenServiceStatus() {
        return tokenServiceStatus;
    }

    public void setTokenServiceStatus(TokenServiceStatus tokenServiceStatus) {
        if (tokenServiceStatus == null) {
            throw new NullPointerException("The token service status should not be set to Null");
        }
        this.tokenServiceStatus = tokenServiceStatus;
    }
}
