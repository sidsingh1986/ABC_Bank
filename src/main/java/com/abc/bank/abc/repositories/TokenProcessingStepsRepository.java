package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.TokenProcessingSteps;
import com.abc.bank.abc.enums.TokenServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TokenProcessingStepsRepository extends JpaRepository<TokenProcessingSteps, Integer> {

    @Query(value = "select token_steps.* from Token_processing_steps token_steps where token_steps.Token_id = :tokenId", nativeQuery = true)
    List<TokenProcessingSteps> findTokenProcessingStepsById(@Param("tokenId") Integer tokenId);

    @Query(value = "update Token_processing_steps set status = 'CANCELLED' where Token_id = :tokenId " +
            "AND status <> 'COMPLETED' ", nativeQuery = true)
    boolean cancelTokenProcessingStepsForToken(Integer tokenId);

    @Query(value = "select token_steps.* from Token_processing_steps token_steps where Token_id = :tokenId " +
            "AND status = :tokenServiceStatus order by id limit 1", nativeQuery = true)
    TokenProcessingSteps getTokenProcessingStepForStatus(int tokenId, String tokenServiceStatus);

    @Query(value = "select token_steps.* from Token_processing_steps token_steps where token_service_id = :serviceId " +
            "AND status = 'ISSUED' order by id limit 1", nativeQuery = true)
    TokenProcessingSteps getHighestOrderTokenProcessingStepForMultiCounterService(int serviceId);
}
