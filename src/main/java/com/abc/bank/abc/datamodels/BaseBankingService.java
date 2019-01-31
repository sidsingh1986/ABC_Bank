package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.enums.ServiceProcessingType;

public abstract class BaseBankingService {

    public ServiceProcessingType getServiceProcessingType() {
        return serviceProcessingType;
    }

    public void setServiceProcessingType(ServiceProcessingType serviceProcessingType) {
        if (serviceProcessingType == null) {
            throw new NullPointerException("the service processing type can't be null");
        }
        this.serviceProcessingType = serviceProcessingType;
    }

    private ServiceProcessingType serviceProcessingType;

}
