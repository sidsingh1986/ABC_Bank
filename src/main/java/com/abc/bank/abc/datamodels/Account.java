package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.enums.AccountType;
import com.abc.bank.abc.enums.Currency;

import javax.persistence.*;

@Entity
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
