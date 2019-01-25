package com.abc.bank.abc.ViewModels;

import com.abc.bank.abc.Enums.TokenStatus;
import com.abc.bank.abc.DataModels.Token;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TokenModel {

    private int id;

    @NotNull
    private CustomerModel customer;

    @NotNull
    private TokenStatus status;

    @NotNull
    private List<ServicesPlaceholder> bankingServices;

    public Token convertToEntity() {
        Token token = new Token();
        token.setId(this.getId());
        token.setStatus(this.getStatus());
        token.setCustomer(this.getCustomer().convertToEntity());

        token.setBankingServicesPlaceholder(this.getBankingServices());

        return token;
    }
}
