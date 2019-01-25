package com.abc.bank.abc.Services;

import com.abc.bank.abc.DataModels.Employee;
import com.abc.bank.abc.DataModels.Token;

import java.util.List;

public interface TokenProcessingService {
    Token createToken(Token token);

    void assignCounter(Integer tokenId, Integer branchId);

    Token getToken(Integer tokenId);

    List<Token> getTokens();

    void updateToken(Token token);

    void deleteToken(Integer tokenId);

    Token pickToken(Token token);

    Token processToken(Integer branchId, Token token, String comments, Employee servedBy);
}
