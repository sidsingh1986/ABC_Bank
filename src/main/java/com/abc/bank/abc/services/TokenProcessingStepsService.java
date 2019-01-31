package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.TokenProcessingSteps;
import com.abc.bank.abc.enums.TokenServiceStatus;

import java.util.List;

public interface TokenProcessingStepsService {

    TokenProcessingSteps createTokenProcessingStep(TokenProcessingSteps tokenProcessingSteps);

    List<TokenProcessingSteps> getTokenProcessingStepsForToken(Integer tokenId);

    void updateTokenProcessingStep(TokenProcessingSteps tokenProcessingSteps);

    TokenProcessingSteps getTokenProcessingSteps(Integer id);

    TokenProcessingSteps getStatusTokenProcessingStepForToken(int id, TokenServiceStatus tokenServiceStatus);

    TokenProcessingSteps getHighestOrderPendingTokenProcessingStep(int serviceId);
}
