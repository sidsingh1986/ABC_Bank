package com.abc.bank.abc.Models;

import com.abc.bank.abc.Enums.TypeOfService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_service")
    private TypeOfService typeOfService;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Counter_Services", joinColumns=@JoinColumn(name="Counter_id"), inverseJoinColumns=@JoinColumn(name="Services_id"))
    private List<BankingService> servicesOffered;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "Counter_id")
    private List<Token> tokens;
}
