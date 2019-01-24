package com.abc.bank.abc.DtoModels;

import com.abc.bank.abc.Models.Bank;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BankDTO {

    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    public Bank convertToEntity() {
        Bank bank = new Bank();
        bank.setId(this.getId());
        bank.setName(this.getName());

        return bank;
    }
}
