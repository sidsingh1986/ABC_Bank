package com.abc.bank.abc.DataModels;

import com.abc.bank.abc.ViewModels.BankingServiceModel;
import com.abc.bank.abc.ViewModels.CounterModel;
import com.abc.bank.abc.ViewModels.TokenModel;
import com.abc.bank.abc.Enums.CustomerType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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
    private CustomerType customerType;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Counter_Services", joinColumns=@JoinColumn(name="Counter_id"), inverseJoinColumns=@JoinColumn(name="Services_id"))
    private List<BankingService> servicesOffered;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "Counter_id")
    private List<Token> tokens;

    public CounterModel convertToDTO() {

        CounterModel counter = new CounterModel();

        counter.setId(this.getId());
        counter.setName(this.getName());
        counter.setCustomerType(this.getCustomerType());

        List<BankingService> bankingServices = this.getServicesOffered();

        List<BankingServiceModel> bankingServicesDTOS = new ArrayList<>();
        for (int index = 0; index < bankingServices.size(); index++) {
            bankingServicesDTOS.add(bankingServices.get(index).convertToDTO());
        }
        counter.setServicesOffered(bankingServicesDTOS);

        List<Token> tokens = this.getTokens();

        List<TokenModel> tokensDTOS = new ArrayList<>();

        for (int index = 0;index < tokens.size(); index++) {
            tokensDTOS.add(tokens.get(index).convertToDTO());
        }

        counter.setTokens(tokensDTOS);

        return counter;
    }
}
