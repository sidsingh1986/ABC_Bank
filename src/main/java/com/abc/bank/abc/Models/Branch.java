package com.abc.bank.abc.Models;

import com.abc.bank.abc.DtoModels.*;
import com.abc.bank.abc.Enums.ServiceProcessingType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Null;
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

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Branch_Services", joinColumns=@JoinColumn(name="Branch_id"), inverseJoinColumns=@JoinColumn(name="Services_id"))
    private List<BankingService> bankingServices;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Branch_Multi_counter_services", joinColumns=@JoinColumn(name="Branch_id"), inverseJoinColumns=@JoinColumn(name="Multi_counter_service_id"))
    private List<MultiCounterBankingService> multiCounterBankingServices;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "Branch_id")
    List<Counter> counters;

    public BranchDTO convertToDTO() {
        BranchDTO branch = new BranchDTO();
        branch.setId(this.getId());
        branch.setName(this.getName());
        branch.setAddress(this.getAddress().convertToDTO());
        branch.setBank(this.getBank().convertToDTO());

        List<Counter> counters = this.getCounters();
        List<CounterDTO> counterDTOS = new ArrayList<>();
        for (int index = 0; index < counters.size(); index++) {
            counterDTOS.add(counters.get(index).convertToDTO());
        }
        branch.setCounters(counterDTOS);

        List<BankingServiceInterface> bankingServiceList = new ArrayList<>();
        List<MultiCounterBankingService> multiCounterBankingServices = this.getMultiCounterBankingServices();
        List<BankingService> bankingServices = this.getBankingServices();
        for (int index = 0; index < bankingServices.size(); index++) {
            BankingServiceDTO bankingServiceDTO = bankingServices.get(index).convertToDTO();
            bankingServiceDTO.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
            bankingServiceList.add(bankingServiceDTO);
        }

        for (int index = 0; index < multiCounterBankingServices.size(); index++) {
            MultiCounterBankingServiceDTO multiCounterBankingServiceDTO = multiCounterBankingServices.get(index).convertToDTO();
            multiCounterBankingServiceDTO.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
            bankingServiceList.add(multiCounterBankingServiceDTO);
        }

        branch.setBankingServices(bankingServiceList);

        return branch;
    }
}
