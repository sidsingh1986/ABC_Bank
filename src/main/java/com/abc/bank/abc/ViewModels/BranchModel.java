package com.abc.bank.abc.ViewModels;

import com.abc.bank.abc.Enums.ServiceProcessingType;
import com.abc.bank.abc.DataModels.BankingService;
import com.abc.bank.abc.DataModels.Branch;
import com.abc.bank.abc.DataModels.Counter;
import com.abc.bank.abc.DataModels.MultiCounterBankingService;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class BranchModel {

    private int id;

    @NotNull(message = "name can't be null")
    @Size(min = 1, max = 45, message = "size of name has to between 1 and 45 characters")
    private String name;

    @NotNull
    private AddressModel address;

    @NotNull
    private BankModel bank;

    @NotNull
    private List<BankingServiceInterface> bankingServices;

    List<CounterModel> counters;

    public Branch convertToEntity() {
        Branch branch = new Branch();
        branch.setId(this.getId());
        branch.setName(this.getName());
        branch.setAddress(this.getAddress().convertToEntity());
        branch.setBank(this.getBank().convertToEntity());

        List<CounterModel> counterModels = this.getCounters();
        List<Counter> counters = new ArrayList<>();
        for (int index = 0; index < counterModels.size(); index++) {
            counters.add(counterModels.get(index).convertToEntity());
        }
        branch.setCounters(counters);

        List<BankingServiceInterface> bankingServiceList = this.getBankingServices();
        List<MultiCounterBankingService> multiCounterBankingServices = new ArrayList<>();
        List<BankingService> bankingServices = new ArrayList<>();
        for (int index = 0; index < bankingServiceList.size(); index++) {
            if (bankingServiceList.get(index).getServiceProcessingType().equals(ServiceProcessingType.MULTI_COUNTER)) {
                MultiCounterBankingServiceModel multiCounterBankingServiceModel = (MultiCounterBankingServiceModel)bankingServiceList.get(index);
                multiCounterBankingServices.add(multiCounterBankingServiceModel.convertToEntity());
            } else {
                BankingServiceModel bankingServiceModel = (BankingServiceModel) bankingServiceList.get(index);
                bankingServices.add(bankingServiceModel.convertToEntity());
            }
        }
        branch.setMultiCounterBankingServices(multiCounterBankingServices);
        branch.setBankingServices(bankingServices);

        return branch;
    }
}
