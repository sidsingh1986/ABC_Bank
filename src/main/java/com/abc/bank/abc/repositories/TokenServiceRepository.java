package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.TokenService;
import com.abc.bank.abc.enums.TokenServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenServiceRepository extends JpaRepository<TokenService, Integer> {

    @Query(value = "select token_service.* from Token_Services token_service where token_service.Token_id = :tokenId " +
            "AND token_service.status = :tokenServiceStatus order by processing_order limit 1", nativeQuery = true)
    TokenService getHighestOrderTokenService(int tokenId, TokenServiceStatus tokenServiceStatus);

    @Query(value = "update Token_Services set status = 'CANCELLED' where Token_id = :tokenId " +
            "AND status <> 'COMPLETED' ", nativeQuery = true)
    boolean cancelAllServicesForToken(Integer tokenId);
}
