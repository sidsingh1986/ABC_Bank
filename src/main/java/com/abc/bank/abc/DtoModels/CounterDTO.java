package com.abc.bank.abc.DtoModels;

import com.abc.bank.abc.Enums.CustomerType;
import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.Counter;
import com.abc.bank.abc.Models.Token;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class CounterDTO {

    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    private CustomerType customerType;

    private List<BankingServiceDTO> servicesOffered;

    private List<TokenDTO> tokens;

    public Counter convertToEntity() {
        Counter counter = new Counter();

        counter.setId(this.getId());
        counter.setName(this.getName());
        counter.setCustomerType(this.getCustomerType());

        List<BankingServiceDTO> bankingServiceDTOS = this.getServicesOffered();

        List<BankingService> bankingServices = new ArrayList<>();
        for (int index = 0; index < bankingServiceDTOS.size(); index++) {
            bankingServices.add(bankingServiceDTOS.get(index).convertToEntity());
        }
        counter.setServicesOffered(bankingServices);

        List<TokenDTO> tokenDTOS = this.getTokens();

        List<Token> tokens = new ArrayList<>();

        for (int index = 0;index < tokenDTOS.size(); index++) {
            tokens.add(tokenDTOS.get(index).convertToEntity());
        }

        counter.setTokens(tokens);

        return counter;
    }
}
