package com.abc.bank.abc.ViewModels;

import com.abc.bank.abc.Enums.ServiceProcessingType;
import com.abc.bank.abc.DataModels.BankingService;
import com.abc.bank.abc.DataModels.MultiCounterBankingService;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class MultiCounterBankingServiceModel extends BankingServiceInterface {

    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    List<BankingServiceModel> bankingServices;

    public MultiCounterBankingService convertToEntity() {
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
