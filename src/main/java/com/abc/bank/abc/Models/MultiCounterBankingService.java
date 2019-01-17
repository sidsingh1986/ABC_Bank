package com.abc.bank.abc.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Multi_counter_service")
public class MultiCounterBankingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Multi_counter_service_has_Services", joinColumns = { @JoinColumn(name = "Multi_counter_service_id") }, inverseJoinColumns = { @JoinColumn(name = "Services_id") })
    List<BankingService> bankingServices;
}
