package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.TokenMultiCounterService;
import com.abc.bank.abc.enums.TokenServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TokenMultiCounterServiceRepository extends JpaRepository<TokenMultiCounterService, Integer> {

    @Query(value = "select multi_counter_service.* from Token_Multi_counter_service multi_counter_service where multi_counter_service.Token_id = :tokenId " +
            "AND multi_counter_service.status = :tokenServiceStatus order by processing_order limit 1", nativeQuery = true)
    TokenMultiCounterService getHighestOrderMultiCounterService(int tokenId, TokenServiceStatus tokenServiceStatus);

    @Query(value = "update Token_Multi_counter_service set status = 'CANCELLED' where Token_id = :tokenId " +
            "AND status <> 'COMPLETED' ", nativeQuery = true)
    boolean cancelAllMultiCounterServicesForToken(Integer tokenId);

    @Query(value = "select multi_counter_service.* from Token_Multi_counter_service multi_counter_service where multi_counter_service.Token_id = :tokenId order by processing_order", nativeQuery = true)
    List<TokenMultiCounterService> getAllTokenServicesForTokenOrderByProcessingOrder(Integer tokenId);
}
