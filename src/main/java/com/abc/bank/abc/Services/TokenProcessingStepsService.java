package com.abc.bank.abc.Services;

import com.abc.bank.abc.DataModels.TokenProcessingSteps;

import java.util.List;

public interface TokenProcessingStepsService {

    TokenProcessingSteps createTokenProcessingStep(TokenProcessingSteps tokenProcessingSteps);

    List<TokenProcessingSteps> getTokenProcessingStepsForToken(Integer tokenId);

    void updateTokenProcessingStep(TokenProcessingSteps tokenProcessingSteps);

    TokenProcessingSteps getTokenProcessingSteps(Integer id);
}
