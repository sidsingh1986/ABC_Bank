package com.abc.bank.abc.services;

import com.abc.bank.abc.enums.TokenServiceStatus;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.datamodels.TokenProcessingSteps;
import com.abc.bank.abc.repositories.TokenProcessingStepsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenProcessingStepsServiceImpl implements TokenProcessingStepsService {

    @Autowired
    TokenProcessingStepsRepository tokenProcessingStepsRepository;

    @Override
    public TokenProcessingSteps createTokenProcessingStep(TokenProcessingSteps tokenProcessingSteps) {
        return tokenProcessingStepsRepository.save(tokenProcessingSteps);
    }

    @Override
    public List<TokenProcessingSteps> getTokenProcessingStepsForToken(Integer tokenId) {
        return tokenProcessingStepsRepository.findTokenProcessingStepsById(tokenId);
    }

    @Override
    public void updateTokenProcessingStep(TokenProcessingSteps tokenProcessingSteps) {
        tokenProcessingStepsRepository.save(tokenProcessingSteps);
    }

    @Override
    public TokenProcessingSteps getTokenProcessingSteps(Integer id) {
        Optional<TokenProcessingSteps> optional = tokenProcessingStepsRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResourceNotFoundException(id, "Token Processing steps not found");
        }
    }

    @Override
    public TokenProcessingSteps getStatusTokenProcessingStepForToken(int id, TokenServiceStatus tokenServiceStatus) {
        return null;
    }

    @Override
    public TokenProcessingSteps getHighestOrderPendingTokenProcessingStep(int serviceId) {
        return null;
    }
}
