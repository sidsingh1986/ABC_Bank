package com.abc.bank.abc.Services;

import com.abc.bank.abc.Enums.TypeOfService;
import com.abc.bank.abc.Models.Customer;
import com.abc.bank.abc.Models.ServicesPlaceholder;
import com.abc.bank.abc.Models.Token;

import java.util.List;

public interface TokenProcessingService {
    Token createToken(Customer customer, ServicesPlaceholder servicesPlaceholder, int branchId);

    void completeToken(Token token);

    void cancelToken(Token token);

    void assignCounter(Integer tokenId, Integer branchId, Integer serviceId);

    Token getToken(Integer tokenId);

    List<Token> getTokens();

    void setTokenStatusToProcess(Token token);

    TypeOfService getTypeOfServiceforToken(Integer tokenId);
}
