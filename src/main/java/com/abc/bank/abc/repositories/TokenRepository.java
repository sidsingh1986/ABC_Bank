package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = "select token.* from Token token where token.Counter_id = :counterId " +
            "AND token.status = :'COUNTER_ASSIGNED' order by processing_order limit 1", nativeQuery = true)
    Token getNextToken(Integer counterId);

    @Query(value = "select token from Token token where token.Counter_id = :counterId " +
            "AND token.status = :'IN_PROCESS'", nativeQuery = true)
    Token getCurrentToken(Integer counterId);
}
