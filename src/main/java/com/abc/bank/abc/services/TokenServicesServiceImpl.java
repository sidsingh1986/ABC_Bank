package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.TokenProcessingSteps;
import com.abc.bank.abc.datamodels.TokenService;
import com.abc.bank.abc.enums.ServiceProcessingType;
import com.abc.bank.abc.enums.TokenServiceStatus;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.repositories.TokenServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenServicesServiceImpl implements TokenServicesService {

    @Autowired
    TokenServiceRepository tokenServiceRepository;

    @Override
    public TokenService createTokenService(TokenService tokenService) {
        return tokenServiceRepository.save(tokenService);
    }

    @Override
    public TokenService getTokenService(Integer serviceId) {
        Optional<TokenService> optional = tokenServiceRepository.findById(serviceId);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResourceNotFoundException(serviceId, "Token multi counter service not found");
        }
    }

    @Override
    public TokenService getHighestOrderPendingTokenService(int tokenId) {
        return tokenServiceRepository.getHighestOrderTokenService(tokenId, TokenServiceStatus.QUEUED);
    }

    @Override
    public TokenService updateTokenServiceStatus(TokenService tokenService, TokenServiceStatus tokenServiceStatus) {

        if (tokenService == null) {
            throw new NullPointerException("The token service to update can't be null");
        }

        tokenService.setStatus(tokenServiceStatus);
        return tokenServiceRepository.save(tokenService);
    }

    @Override
    public boolean cancelAllServicesForToken(Integer tokenId) {
        return tokenServiceRepository.cancelAllServicesForToken(tokenId);
    }

    @Override
    public List<TokenService> getAllTokenServicesForToken(Integer tokenId) {
        return tokenServiceRepository.getAllTokenServicesForTokenOrderByProcessingOrder(tokenId);
    }
}
