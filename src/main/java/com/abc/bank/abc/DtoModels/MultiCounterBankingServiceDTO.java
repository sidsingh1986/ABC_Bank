package com.abc.bank.abc.DtoModels;

import com.abc.bank.abc.Enums.ServiceProcessingType;
import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.MultiCounterBankingService;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class MultiCounterBankingServiceDTO extends BankingServiceInterface {

    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    List<BankingServiceDTO> bankingServices;

    public MultiCounterBankingService convertToEntity() {
        MultiCounterBankingService multiCounterBankingService = new MultiCounterBankingService();

        multiCounterBankingService.setId(this.getId());
        multiCounterBankingService.setName(this.getName());
        List<BankingServiceDTO> bankingServiceDTOS = this.getBankingServices();
        List<BankingService> bankingServices = new ArrayList<>();
        for (int index = 0; index < bankingServiceDTOS.size(); index++) {
            bankingServices.add(bankingServiceDTOS.get(index).convertToEntity());
        }

        multiCounterBankingService.setBankingServices(bankingServices);
        multiCounterBankingService.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
        return multiCounterBankingService;
    }
}
