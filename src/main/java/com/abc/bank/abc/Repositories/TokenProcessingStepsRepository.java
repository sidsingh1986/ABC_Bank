package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.DataModels.TokenProcessingSteps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TokenProcessingStepsRepository extends JpaRepository<TokenProcessingSteps, Integer> {

    @Query(value = "select token_steps from Token_processing_steps token_steps where token_steps.Token_id = :tokenId", nativeQuery = true)
    List<TokenProcessingSteps> findTokenProcessingStepsById(@Param("tokenId") Integer tokenId);
}
