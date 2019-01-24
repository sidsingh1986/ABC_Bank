package com.abc.bank.abc.Models;

import com.abc.bank.abc.DtoModels.BankDTO;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public BankDTO convertToDTO() {
        BankDTO bank = new BankDTO();
        bank.setId(this.getId());
        bank.setName(this.getName());

        return bank;
    }
}
