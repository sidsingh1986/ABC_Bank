package com.abc.bank.abc.viewmodels;

import com.abc.bank.abc.enums.ServiceProcessingType;
import com.abc.bank.abc.datamodels.BankingService;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class BankingServiceModel{

    private int id;

    ServiceProcessingType serviceProcessingType;

    @Size(min = 2, message = "the name should be atleast 2 characters")
    @NotEmpty(message = "The banking service name should not be empty")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ServiceProcessingType getServiceProcessingType() {
        return serviceProcessingType;
    }

    public void setServiceProcessingType(ServiceProcessingType serviceProcessingType) {
        if (serviceProcessingType == null) {
            throw new NullPointerException("The service processing type of a banking service can't be set to Null");
        }
        this.serviceProcessingType = serviceProcessingType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("The name of a banking service can't be set to Null");
        }
        this.name = name;
    }

    public BankingService convertToEntity() {
        BankingService bankingService = new BankingService();
        bankingService.setId(this.getId());
        bankingService.setName(this.getName());
        bankingService.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);

        return bankingService;
    }
}
