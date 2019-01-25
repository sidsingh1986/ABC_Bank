package com.abc.bank.abc.DataModels;

import com.abc.bank.abc.ViewModels.BankModel;
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

    public BankModel convertToDTO() {
        BankModel bank = new BankModel();
        bank.setId(this.getId());
        bank.setName(this.getName());

        return bank;
    }
}
