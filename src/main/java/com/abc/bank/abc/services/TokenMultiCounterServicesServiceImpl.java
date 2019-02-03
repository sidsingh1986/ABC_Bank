package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.TokenMultiCounterService;
import com.abc.bank.abc.enums.TokenServiceStatus;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.repositories.TokenMultiCounterServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenMultiCounterServicesServiceImpl implements TokenMultiCounterServicesService {

    @Autowired
    TokenMultiCounterServiceRepository tokenMultiCounterServiceRepository;

    @Override
    public TokenMultiCounterService createTokenMultiCounterService(TokenMultiCounterService tokenMultiCounterService) {
        return tokenMultiCounterServiceRepository.save(tokenMultiCounterService);
    }

    @Override
    public TokenMultiCounterService getTokenMultiCounterService(Integer serviceId) {
        Optional<TokenMultiCounterService> optional = tokenMultiCounterServiceRepository.findById(serviceId);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResourceNotFoundException(serviceId, "Token multi counter service not found");
        }
    }

    @Override
    public TokenMultiCounterService getHighestOrderPendingMultiCounterService(Integer tokenId) {
        return tokenMultiCounterServiceRepository.getHighestOrderMultiCounterService(tokenId, TokenServiceStatus.QUEUED);
    }

    @Override
    public TokenMultiCounterService updateTokenMultiCounterServiceStatus(TokenMultiCounterService tokenMultiCounterService,
                                                                         TokenServiceStatus tokenServiceStatus) {

        if (tokenMultiCounterService == null) {
            throw new NullPointerException("The token multi counter service to update can't be null");
        }

        tokenMultiCounterService.setStatus(tokenServiceStatus);
        return tokenMultiCounterServiceRepository.save(tokenMultiCounterService);
    }

    @Override
    public boolean cancelAllMultiCounterServicesForToken(Integer tokenId) {
        return tokenMultiCounterServiceRepository.cancelAllMultiCounterServicesForToken(tokenId);
    }
}
