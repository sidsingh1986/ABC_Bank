package com.abc.bank.abc.DataModels;

import com.abc.bank.abc.Enums.ServiceProcessingType;
import com.abc.bank.abc.ViewModels.BankingServiceModel;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Services")
public class BankingService extends BaseBankingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public BankingServiceModel convertToDTO() {
        BankingServiceModel bankingService = new BankingServiceModel();
        bankingService.setId(this.getId());
        bankingService.setName(this.getName());
        bankingService.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        return bankingService;
    }
}
