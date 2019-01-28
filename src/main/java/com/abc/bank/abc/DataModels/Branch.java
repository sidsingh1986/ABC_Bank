package com.abc.bank.abc.DataModels;

import com.abc.bank.abc.ViewModels.*;
import com.abc.bank.abc.Enums.ServiceProcessingType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    private Bank bank;

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name="Branch_Services", joinColumns=@JoinColumn(name="Branch_id"), inverseJoinColumns=@JoinColumn(name="Services_id"))
    private List<BankingService> bankingServices;

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name="Branch_Multi_counter_services", joinColumns=@JoinColumn(name="Branch_id"), inverseJoinColumns=@JoinColumn(name="Multi_counter_service_id"))
    private List<MultiCounterBankingService> multiCounterBankingServices;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "Branch_id")
    List<Counter> counters;

    public BranchModel convertToDTO() {
        BranchModel branch = new BranchModel();
        branch.setId(this.getId());
        branch.setName(this.getName());
        branch.setAddress(this.getAddress().convertToDTO());
        branch.setBank(this.getBank().convertToDTO());

        List<Counter> counters = this.getCounters();
        List<CounterModel> counterModels = new ArrayList<>();
        for (int index = 0; index < counters.size(); index++) {
            counterModels.add(counters.get(index).convertToDTO());
        }
        branch.setCounters(counterModels);

        List<BankingServiceInterface> bankingServiceList = new ArrayList<>();
        List<MultiCounterBankingService> multiCounterBankingServices = this.getMultiCounterBankingServices();
        List<BankingService> bankingServices = this.getBankingServices();
        for (int index = 0; index < bankingServices.size(); index++) {
            BankingServiceModel bankingServiceModel = bankingServices.get(index).convertToDTO();
            bankingServiceModel.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
            bankingServiceList.add(bankingServiceModel);
        }

        for (int index = 0; index < multiCounterBankingServices.size(); index++) {
            MultiCounterBankingServiceModel multiCounterBankingServiceModel = multiCounterBankingServices.get(index).convertToDTO();
            multiCounterBankingServiceModel.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
            bankingServiceList.add(multiCounterBankingServiceModel);
        }

        branch.setBankingServices(bankingServiceList);

        return branch;
    }
}
