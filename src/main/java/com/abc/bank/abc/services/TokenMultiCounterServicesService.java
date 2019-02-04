package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.TokenMultiCounterService;
import com.abc.bank.abc.enums.TokenServiceStatus;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;

import java.util.List;

public interface TokenMultiCounterServicesService {

    /**
     * For creating a Token Multi counter service
     *
     * @param tokenMultiCounterService token multi counter instance to be created
     * @return created token multi counter service instance
     */
    TokenMultiCounterService createTokenMultiCounterService(TokenMultiCounterService tokenMultiCounterService);

    /**
     * For getting a particular Token Multi counter Service
     *
     * @param serviceId token multi counter service identifier
     * @return token multi counter service with service Id
     * @throws ResourceNotFoundException if the Banking service is not part of multi counter service
     */
    TokenMultiCounterService getTokenMultiCounterService(Integer serviceId);

    /**
     * For getting a highest order Token Multi counter Service which is pending to process for a given token
     *
     * @param tokenId token identifier
     * @return token multi counter service with token Id
     * @throws ResourceNotFoundException if the Token multi counter service is not found
     */
    TokenMultiCounterService getHighestOrderPendingMultiCounterService(Integer tokenId);

    /**
     * For updating the status of Token Multi counter Service
     *
     * @param tokenMultiCounterService token multi counter service
     * @return token multi counter service with token Id
     * @throws ResourceNotFoundException if the Token multi counter service is not found
     */
    TokenMultiCounterService updateTokenMultiCounterServiceStatus(TokenMultiCounterService tokenMultiCounterService, TokenServiceStatus tokenServiceStatus);

    /**
     * For cancelling all the Token Multi counter Services for a particular token
     *
     * @param tokenId token identifier
     * @return status of operation
     */
    boolean cancelAllMultiCounterServicesForToken(Integer tokenId);

    /**
     * For getting all the Token Multi counter Services for a particular token
     *
     * @param tokenId token identifier
     * @return list of token multi counter services
     */
    List<TokenMultiCounterService> getAllTokenMultiCounterServicesForToken(Integer tokenId);
}
