package com.abc.bank.abc.ViewModels;

import com.abc.bank.abc.Enums.CustomerType;
import com.abc.bank.abc.DataModels.BankingService;
import com.abc.bank.abc.DataModels.Counter;
import com.abc.bank.abc.DataModels.Token;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class CounterModel {

    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    private CustomerType customerType;

    private List<BankingServiceModel> servicesOffered;

    private List<TokenModel> tokens;

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
