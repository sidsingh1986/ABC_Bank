package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = "select token.* from Token token where token.Counter_id = :counterId " +
            "AND token.status = :'COUNTER_ASSIGNED' order by processing_order limit 1", nativeQuery = true)
    Token getNextToken(Integer counterId);

    @Query(value = "select token.* from Token token where token.Counter_id = :counterId " +
            "AND token.status = :'IN_PROCESS'", nativeQuery = true)
    Token getCurrentToken(Integer counterId);

    @Query(value = "select distinct token.* from Token token join Token_processing_steps token_steps on token.id =  token_steps.Token_id where token.priority = :customerType " +
            "AND token_steps.Services_id IN :bankingServices order by token.id", nativeQuery = true)
    Token pickNextToken(String customerType, List<BankingService> bankingServices);

    @Query(value = "select token.* from Token token order by token.id desc limit 1", nativeQuery = true)
    Token getLastTokenCreated();
}
