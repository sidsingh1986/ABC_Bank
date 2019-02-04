package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.TokenProcessingSteps;
import com.abc.bank.abc.enums.TokenServiceStatus;
import com.abc.bank.abc.exceptions.IllegalInputException;

import java.util.List;

public interface TokenProcessingStepsService {

    /**
     * For creating a new Token Processing step instance
     *
     * @param tokenProcessingSteps token instance to be created
     * @return created token processing steps instance
     */
    TokenProcessingSteps createTokenProcessingStep(TokenProcessingSteps tokenProcessingSteps);

    /**
     * For getting all the token processing steps for a token
     *
     * @param tokenId token identifier
     * @return token processing steps list for a token
     */
    List<TokenProcessingSteps> getTokenProcessingStepsForToken(Integer tokenId);

    /**
     * For updating a particular token processing steps
     *
     * @param tokenProcessingSteps token processing step instance
     * @return token processing steps list for a token
     */
    void updateTokenProcessingStep(TokenProcessingSteps tokenProcessingSteps);

    /**
     * For getting a particular token processing steps based on identifier
     *
     * @param tokenProcessingStepId token processing step identifier
     * @return token processing step
     */
    TokenProcessingSteps getTokenProcessingSteps(Integer tokenProcessingStepId);

    /**
     * For getting a particular token processing steps with a particular status on for a given token
     *
     * @param tokenId token identifier
     * @param tokenServiceStatus token service status
     * @return token processing step
     */
    TokenProcessingSteps getStatusTokenProcessingStepForToken(int tokenId, TokenServiceStatus tokenServiceStatus);

    /**
     * For getting the next highest order pending service processing step for a  multi counter service.
     *
     * @param serviceId service identifier
     * @return token processing step
     * @throws IllegalInputException if called for single counter service as there will be only one token
     * processing step for single counter service.
     */
    TokenProcessingSteps getHighestOrderPendingTokenProcessingStep(int serviceId);

    /**
     * For cancelling all the pending token processing steps of a token. This is called when the token is cancelled.
     *
     * @param tokenId token identifier
     * @return status of operation
     */
    boolean cancelTokenProcessingStepsForToken(Integer tokenId);
}
