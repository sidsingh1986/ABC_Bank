package com.abc.bank.abc.Models;

import com.abc.bank.abc.Enums.AccountType;
import com.abc.bank.abc.Enums.Currency;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private double balance;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    private Customer customer;
}
