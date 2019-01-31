package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.exceptions.IllegalInputException;
import com.abc.bank.abc.viewmodels.*;
import com.abc.bank.abc.enums.ServiceProcessingType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Address_id")
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Bank_id")
    private Bank bank;

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name="Branch_Services", joinColumns=@JoinColumn(name="Branch_id"), inverseJoinColumns=@JoinColumn(name="Services_id"))
    private List<BankingService> bankingServices;

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name="Branch_Multi_counter_services", joinColumns=@JoinColumn(name="Branch_id"), inverseJoinColumns=@JoinColumn(name="Multi_counter_service_id"))
    private List<MultiCounterBankingService> multiCounterBankingServices;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Counter> counters;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Customer> customers;

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
            throw new IllegalInputException("name of branch can't be null");
        }
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (address == null) {
            throw new IllegalInputException("name of branch can't be null");
        }
        this.address = address;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        if (bank == null) {
            throw new IllegalInputException("bank of branch can't be null");
        }
        this.bank = bank;
    }

    public List<BankingService> getBankingServices() {
        return bankingServices;
    }

    public void setBankingServices(List<BankingService> bankingServices) {
        if (bankingServices == null) {
            throw new IllegalInputException("banking services to branch can't be null");
        }
        this.bankingServices = bankingServices;
    }

    public List<MultiCounterBankingService> getMultiCounterBankingServices() {
        return multiCounterBankingServices;
    }

    public void setMultiCounterBankingServices(List<MultiCounterBankingService> multiCounterBankingServices) {
        if (multiCounterBankingServices == null) {
            throw new IllegalInputException("multi counter banking services to branch can't be null");
        }
        this.multiCounterBankingServices = multiCounterBankingServices;
    }

    public List<Counter> getCounters() {
        return counters;
    }

    public void setCounters(List<Counter> counters) {
        if (counters == null) {
            throw new IllegalInputException("counters to branch can't be null");
        }
        this.counters = counters;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        if (customers == null) {
            throw new IllegalInputException("customers to branch can't be null");
        }
        this.customers = customers;
    }

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

        List<BankingServiceModel> bankingServiceList = new ArrayList<>();
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

    public boolean addBankingService(BankingService bankingService) {
        if (bankingService == null) {
            bankingServices = new ArrayList<>();
        }
        return bankingServices.add(bankingService);
    }

    public boolean addMultiCounterBankingService(MultiCounterBankingService multiCounterBankingService) {
        if (multiCounterBankingServices == null) {
            multiCounterBankingServices = new ArrayList<>();
        }
        return multiCounterBankingServices.add(multiCounterBankingService);
    }
}
