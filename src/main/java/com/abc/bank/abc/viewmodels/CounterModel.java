package com.abc.bank.abc.viewmodels;

import com.abc.bank.abc.enums.CustomerType;
import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.Counter;
import com.abc.bank.abc.datamodels.Token;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class CounterModel {

    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    private CustomerType customerType;

    private List<BankingServiceModel> servicesOffered;

    private List<TokenModel> tokens;

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
            throw new NullPointerException("The name of a counter can't be set to Null");
        }
        this.name = name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        if (customerType == null) {
            throw new NullPointerException("The customer type of a counter can't be set to Null");
        }
        this.customerType = customerType;
    }

    public List<BankingServiceModel> getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(List<BankingServiceModel> servicesOffered) {
        if (servicesOffered == null) {
            throw new NullPointerException("The list of services offered by a counter can't be set to Null");
        }
        this.servicesOffered = servicesOffered;
    }

    public List<TokenModel> getTokens() {
        return tokens;
    }

    public void setTokens(List<TokenModel> tokens) {
        if (tokens == null) {
            throw new NullPointerException("The token list of a counter can't be set to Null");
        }
        this.tokens = tokens;
    }

    public Counter convertToEntity() {
        Counter counter = new Counter();

        counter.setId(this.getId());
        counter.setName(this.getName());
        counter.setCustomerType(this.getCustomerType());

        List<BankingServiceModel> bankingServiceRespons = this.getServicesOffered();

        List<BankingService> bankingServices = new ArrayList<>();
        for (int index = 0; index < bankingServiceRespons.size(); index++) {
            bankingServices.add(bankingServiceRespons.get(index).convertToEntity());
        }
        counter.setServicesOffered(bankingServices);

        List<TokenModel> tokenModels = this.getTokens();

        List<Token> tokens = new ArrayList<>();

        for (int index = 0; index < tokenModels.size(); index++) {
            tokens.add(tokenModels.get(index).convertToEntity());
        }

        counter.setTokens(tokens);

        return counter;
    }
}
