package com.abc.bank.abc.viewmodels;

import com.abc.bank.abc.enums.ServiceProcessingType;
import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.Branch;
import com.abc.bank.abc.datamodels.Counter;
import com.abc.bank.abc.datamodels.MultiCounterBankingService;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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
    private List<BankingServiceModel> bankingServices;

    List<CounterModel> counters;

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
            throw new NullPointerException("name of branch can't be null");
        }
        this.name = name;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        if (address == null) {
            throw new NullPointerException("name of branch can't be null");
        }
        this.address = address;
    }

    public BankModel getBank() {
        return bank;
    }

    public void setBank(BankModel bank) {
        if (bank == null) {
            throw new NullPointerException("bank of branch can't be null");
        }
        this.bank = bank;
    }

    public List<BankingServiceModel> getBankingServices() {
        return bankingServices;
    }

    public void setBankingServices(List<BankingServiceModel> bankingServices) {
        if (bankingServices == null) {
            throw new NullPointerException("banking services to branch can't be null");
        }
        this.bankingServices = bankingServices;
    }


    public List<CounterModel> getCounters() {
        return counters;
    }

    public void setCounters(List<CounterModel> counters) {
        if (counters == null) {
            throw new NullPointerException("counters to branch can't be null");
        }
        this.counters = counters;
    }

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

        List<BankingServiceModel> bankingServiceList = this.getBankingServices();
        List<MultiCounterBankingService> multiCounterBankingServices = new ArrayList<>();
        List<BankingService> bankingServices = new ArrayList<>();
        for (int index = 0; index < bankingServiceList.size(); index++) {
            if (bankingServiceList.get(index).getServiceProcessingType().equals(ServiceProcessingType.MULTI_COUNTER)) {
                MultiCounterBankingServiceModel multiCounterBankingServiceModel = (MultiCounterBankingServiceModel)bankingServiceList.get(index);
                multiCounterBankingServices.add(multiCounterBankingServiceModel.convertToSubEntity());
            } else {
                BankingServiceModel bankingServiceModel = bankingServiceList.get(index);
                bankingServices.add(bankingServiceModel.convertToEntity());
            }
        }
        branch.setMultiCounterBankingServices(multiCounterBankingServices);
        branch.setBankingServices(bankingServices);

        return branch;
    }
}
