package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.enums.ServiceProcessingType;
import com.abc.bank.abc.viewmodels.BankingServiceModel;

import javax.persistence.*;

@Entity
@Table(name = "services")
public class BankingService extends BaseBankingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public BankingService(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public BankingService() {
    }

    public void setName(String name) {

        if(name == null) {
            throw new NullPointerException("the name of service can't be null");
        }
        this.name = name;
    }

    public void  setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return  this.id;
    }

    public BankingServiceModel convertToDTO() {
        BankingServiceModel bankingService = new BankingServiceModel();
        bankingService.setId(this.getId());
        bankingService.setName(this.getName());
        bankingService.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        return bankingService;
    }
}
