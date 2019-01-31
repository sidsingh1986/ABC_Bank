package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.viewmodels.BankingServiceModel;
import com.abc.bank.abc.viewmodels.MultiCounterBankingServiceModel;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Multi_counter_service")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MultiCounterBankingService extends BaseBankingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Multi_counter_service_has_Services", joinColumns = { @JoinColumn(name = "Multi_counter_service_id") }, inverseJoinColumns = { @JoinColumn(name = "Services_id") })
    List<BankingService> bankingServices;

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

    public List<BankingService> getBankingServices() {
        return bankingServices;
    }

    public void setBankingServices(List<BankingService> bankingServices) {
        if (bankingServices == null) {
            throw new NullPointerException("The banking services of a multi counter banking service can't be set to null");
        }
        this.bankingServices = bankingServices;
    }

    public boolean addBankingService(BankingService bankingService) {
        if (bankingServices != null) {
            return bankingServices.add(bankingService);
        } else {
            bankingServices = new ArrayList<>();
            return bankingServices.add(bankingService);
        }
    }

    public boolean removeBankingService(BankingService bankingService) {
        if (bankingServices != null) {
            return bankingServices.remove(bankingService);
        } else {
            throw new NullPointerException("the banking service list null for this Multi counter banking service");
        }
    }

    public MultiCounterBankingServiceModel convertToDTO() {
        MultiCounterBankingServiceModel multiCounterBankingService = new MultiCounterBankingServiceModel();

        multiCounterBankingService.setId(this.getId());
        multiCounterBankingService.setName(this.getName());
        List<BankingService> bankingService = this.getBankingServices();
        List<BankingServiceModel> bankingServiceRespons = new ArrayList<>();
        for (int index = 0; index < bankingService.size(); index++) {
            bankingServiceRespons.add(bankingService.get(index).convertToDTO());
        }

        multiCounterBankingService.setBankingServices(bankingServiceRespons);
        return multiCounterBankingService;
    }
}
