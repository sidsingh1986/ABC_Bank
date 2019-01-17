package com.abc.bank.abc.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    private Bank bank;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Branch_Services", joinColumns=@JoinColumn(name="Branch_id"), inverseJoinColumns=@JoinColumn(name="Services_id"))
    private List<BankingService> bankingServices;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Branch_Multi_counter_services", joinColumns=@JoinColumn(name="Branch_id"), inverseJoinColumns=@JoinColumn(name="Multi_counter_service_id"))
    private List<MultiCounterBankingService> multiCounterBankingServices;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "Branch_id")
    List<Counter> counters;
}
