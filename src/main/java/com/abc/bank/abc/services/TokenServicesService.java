package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.TokenService;
import com.abc.bank.abc.enums.TokenServiceStatus;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;

public interface TokenServicesService {

    /**
     * For creating a new Token service
     *
     * @param tokenService token instance to be created
     * @return created token instance
     */
    TokenService createTokenService(TokenService tokenService);

    /**
     * For getting a particular Token service based on id.
     *
     * @param serviceId token service identifier
     * @return Token instance
     * @throws ResourceNotFoundException if the Banking service is not part of multi counter service
     */
    TokenService getTokenService(Integer serviceId);

    /**
     * For getting a highest order Token service for a token which is pending to be processed
     *
     * @param tokenId token identifier
     * @return Token service instance
     * @throws ResourceNotFoundException if the Banking service is not part of multi counter service
     */
    TokenService getHighestOrderPendingTokenService(int tokenId);

    /**
     * For updating the status of Token service
     *
     * @param tokenService token service instance to update
     * @param tokenServiceStatus token service status to be updated
     * @return updated Token service instance
     */
    TokenService updateTokenServiceStatus(TokenService tokenService, TokenServiceStatus tokenServiceStatus);
}
