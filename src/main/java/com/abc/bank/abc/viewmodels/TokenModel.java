package com.abc.bank.abc.viewmodels;

import com.abc.bank.abc.enums.TokenStatus;
import com.abc.bank.abc.datamodels.Token;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

public class TokenModel {

    private int id;

    @NotNull
    private CustomerModel customer;

    @NotNull
    private TokenStatus status;

    @NotNull
    private List<ServicesPlaceholder> bankingServices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        if (customer == null) {
            throw new NullPointerException("The customer to the token can't be set to Null");
        }
        this.customer = customer;
    }

    public TokenStatus getStatus() {
        return status;
    }

    public void setStatus(TokenStatus status) {
        if (status == null) {
            throw new NullPointerException("The status of the token can't be set to Null");
        }
        this.status = status;
    }

    public List<ServicesPlaceholder> getBankingServices() {
        return bankingServices;
    }

    public void setBankingServices(List<ServicesPlaceholder> bankingServices) {
        if (bankingServices == null) {
            throw new NullPointerException("The banking services to the token can't be set to Null");
        }
        this.bankingServices = bankingServices;
    }

    public Token convertToEntity() {
        Token token = new Token();
        token.setId(this.getId());
        token.setStatus(this.getStatus());
        token.setCustomer(this.getCustomer().convertToEntity());

        token.setBankingServicesPlaceholder(this.getBankingServices());

        return token;
    }
}
