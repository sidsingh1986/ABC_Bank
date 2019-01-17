package com.abc.bank.abc.Models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Services")
public class BankingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
