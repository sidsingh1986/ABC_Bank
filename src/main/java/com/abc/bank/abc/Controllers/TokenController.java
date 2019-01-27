package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.ViewModels.TokenModel;
import com.abc.bank.abc.ViewModels.TokenProcessParameters;
import com.abc.bank.abc.DataModels.Token;
import com.abc.bank.abc.Services.TokenProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("token-management")
@RestController
public class TokenController {

    @Autowired
    TokenProcessingService tokenProcessingService;

    /**
     * For getting the list of all the tokens.
     *
     * @return list of tokens
     */
    @GetMapping(value = "/tokens")
    public List<TokenModel> getTokens() {

        List<Token> tokens = tokenProcessingService.getTokens();
        List<TokenModel> tokenModels = new ArrayList<>();

        for (int index = 0; index < tokens.size(); index++) {
            tokenModels.add(tokens.get(index).convertToDTO());
        }
        return tokenModels;
    }

    /**
     * For getting a particuler Token based on id.
     *
     * @param tokenId token identifier
     * @return Token instance
     */
    @GetMapping(value = "/tokens/{id}")
    public TokenModel getToken(@PathVariable(value = "id") Integer tokenId) {
        return tokenProcessingService.getToken(tokenId).convertToDTO();
    }

    /**
     * For creating a new Token
     *
     * @param tokenModel token model instance to be created
     * @return created token instance
     */
    @PostMapping(value = "/tokens")
    public TokenModel createToken(@Valid @RequestBody TokenModel tokenModel) {
        Token token = tokenModel.convertToEntity();
        return tokenProcessingService.createToken(token).convertToDTO();
    }

    /**
     * For updating a existing Token
     *
     * @param tokenModel token model instance to be updated
     */
    @PutMapping(value = "/tokens")
    public void updateToken(@Valid @RequestBody TokenModel tokenModel) {
        Token token = tokenModel.convertToEntity();
        tokenProcessingService.updateToken(token);
    }

    /**
     * Processes the token by taking the service or step of service for which counter is assigned and
     * adding action/comments for the selected step. It also takes care of re-assigning the token to
     * another counter if the token requires some other service or step of service to be processed.
     *
     * @param branchId branch identifier
     * @param tokenId token identifier
     * @throws IllegalArgumentException if the service or step of service is not served by the counter
     * on which token is assigned or if there is multiple steps are created for a single counter service.
     * Or the if service processing type value passed is not SINGLE_COUNTER or MULTI_COUNTER.
     */
    @PutMapping(value = "/branch/{branchId}/tokens/{tokenId}/process")
    public void processToken(@PathVariable(value = "branchId") Integer branchId,
                             @PathVariable(value = "tokenId") Integer tokenId,
                             @Valid @RequestBody TokenProcessParameters tokenProcessParameters) {
        Token token = tokenProcessingService.getToken(tokenId);
        tokenProcessingService.processToken(branchId, token, tokenProcessParameters.getComments(),
                tokenProcessParameters.getEmployee().convertToEntity());
    }

    /**
     * Assigns a token to counter based on the highest order service selected in counter. It checks for the
     * counter serving the highest order pending service and then assigns the token to counter which has the
     * minimum number of tokens to be served.
     *
     * @param branchId branch identifier
     * @param tokenId token identifier
     * @throws IllegalArgumentException if there are no services pending in the token to be served
     */
    @PutMapping(value = "/branch/{branchId}/tokens/{tokenId}/assign")
    public void assignCounter(@PathVariable(value = "branchId") Integer branchId,
                             @PathVariable(value = "tokenId") Integer tokenId) {
        tokenProcessingService.assignCounter(tokenId, branchId);
    }
}
