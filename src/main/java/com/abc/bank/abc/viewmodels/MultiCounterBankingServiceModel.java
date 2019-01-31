package com.abc.bank.abc.viewmodels;

import com.abc.bank.abc.enums.ServiceProcessingType;
import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.MultiCounterBankingService;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class MultiCounterBankingServiceModel extends BankingServiceModel {

    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    List<BankingServiceModel> bankingServices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("The name of a Multi counter banking service can't be set to null");
        }
        this.name = name;
    }

    public List<BankingServiceModel> getBankingServices() {
        return bankingServices;
    }

    public void setBankingServices(List<BankingServiceModel> bankingServices) {
        if (bankingServices == null) {
            throw new NullPointerException("The banking services of a multi counter banking service can't be set to null");
        }
        this.bankingServices = bankingServices;
    }

    public MultiCounterBankingService convertToSubEntity() {
        MultiCounterBankingService multiCounterBankingService = new MultiCounterBankingService();

        multiCounterBankingService.setId(this.getId());
        multiCounterBankingService.setName(this.getName());
        List<BankingServiceModel> bankingServiceRespons = this.getBankingServices();
        List<BankingService> bankingServices = new ArrayList<>();
        for (int index = 0; index < bankingServiceRespons.size(); index++) {
            bankingServices.add(bankingServiceRespons.get(index).convertToEntity());
        }

        multiCounterBankingService.setBankingServices(bankingServices);
        multiCounterBankingService.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
        return multiCounterBankingService;
    }
}
