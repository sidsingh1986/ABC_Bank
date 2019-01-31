package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.Employee;
import com.abc.bank.abc.datamodels.Token;

import java.util.List;

public interface TokenProcessingService {

    /**
     * For creating a new Token
     *
     * @param token token instance to be created
     * @return created token instance
     */
    Token createToken(Token token);

    /**
     * Assigns a token to counter based on the highest order service selected in counter. It checks for the
     * counter serving the highest order pending service and then assigns the token to counter which has the
     * minimum number of tokens to be served.
     *
     * @param branchId branch identifier
     * @param tokenId token identifier
     * @throws IllegalArgumentException if there are no services pending in the token to be served
     */
    void assignCounter(Integer tokenId, Integer branchId);

    /**
     * For getting a particuler Token based on id.
     *
     * @param tokenId token identifier
     * @return Token instance
     */
    Token getToken(Integer tokenId);

    /**
     * For getting the list of all the tokens.
     *
     * @return list of tokens
     */
    List<Token> getTokens();

    /**
     * For updating a existing Token
     *
     * @param tokenId
     * @param token token model instance to be updated
     */
    Token updateToken(Integer tokenId, Token token);

    void deleteToken(Integer tokenId);

    /**
     * For picking the next token which will be processed by counter. It changes the status of
     * token to IN_PROCESS.
     *
     * @param token token instance
     * @return Token to process
     */
    Token pickToken(Token token);

    /**
     * Processes the token by taking the service or step of service for which counter is assigned and
     * adding action/comments for the selected step. It also takes care of re-assigning the token to
     * another counter if the token requires some other service or step of service to be processed.
     *
     * @param branchId branch identifier
     * @param token token instance
     * @throws IllegalArgumentException if the service or step of service is not served by the counter
     * on which token is assigned or if there is multiple steps are created for a single counter service.
     * Or the if service processing type value passed is not SINGLE_COUNTER or MULTI_COUNTER.
     */
    Token processToken(Integer branchId, Token token, String comments, Employee servedBy);

    Token getNextToken(Integer counterId);

    Token getCurrentToken(Integer counterId);
}
