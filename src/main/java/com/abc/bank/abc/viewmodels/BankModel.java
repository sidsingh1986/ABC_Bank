package com.abc.bank.abc.viewmodels;

import com.abc.bank.abc.datamodels.Bank;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BankModel {

    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

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
            throw new NullPointerException("The name parameter for bank can't be null");
        }
        this.name = name;
    }

    public Bank convertToEntity() {
        Bank bank = new Bank();
        bank.setId(this.getId());
        bank.setName(this.getName());

        return bank;
    }
}
