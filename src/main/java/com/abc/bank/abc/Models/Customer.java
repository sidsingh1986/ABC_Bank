package com.abc.bank.abc.Models;

import com.abc.bank.abc.Enums.TypeOfService;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_service")
    private TypeOfService typeOfService;

    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    private Bank bank;
}
