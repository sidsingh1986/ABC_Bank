package com.abc.bank.abc.Models;

import com.abc.bank.abc.DtoModels.BankingServiceDTO;
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

    public BankingServiceDTO convertToDTO() {
        BankingServiceDTO bankingService = new BankingServiceDTO();
        bankingService.setId(this.getId());
        bankingService.setName(this.getName());
        bankingService.setServiceProcessingType(this.getServiceProcessingType());
        return bankingService;
    }
}
