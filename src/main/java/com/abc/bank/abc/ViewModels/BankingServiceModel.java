package com.abc.bank.abc.ViewModels;

import com.abc.bank.abc.Enums.ServiceProcessingType;
import com.abc.bank.abc.DataModels.BankingService;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class BankingServiceModel extends BankingServiceInterface{

    private int id;


    @Size(min = 2, message = "the name should be atleast 2 characters")
    @NotEmpty(message = "The banking service name should not be empty")
    private String name;

    public BankingService convertToEntity() {
        BankingService bankingService = new BankingService();
        bankingService.setId(this.getId());
        bankingService.setName(this.getName());
        bankingService.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);

        return bankingService;
    }
}
