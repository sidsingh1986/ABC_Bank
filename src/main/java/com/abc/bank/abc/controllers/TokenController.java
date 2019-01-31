package com.abc.bank.abc.controllers;

import com.abc.bank.abc.viewmodels.TokenModel;
import com.abc.bank.abc.viewmodels.TokenProcessParameters;
import com.abc.bank.abc.datamodels.Token;
import com.abc.bank.abc.services.TokenProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
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
    public ResponseEntity<List<Token>> getTokens() {

        List<Token> tokens = tokenProcessingService.getTokens();

        if (tokens == null) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    /**
     * For getting a particular Token based on id.
     *
     * @param tokenId token identifier
     * @return Token instance
     */
    @GetMapping(value = "/tokens/{id}")
    public ResponseEntity<Token> getToken(@PathVariable(value = "id") Integer tokenId) {
        return new ResponseEntity<>(tokenProcessingService.getToken(tokenId), HttpStatus.OK);
    }

    /**
     * For creating a new Token
     *
     * @param tokenModel token model instance to be created
     * @return created token instance
     */
    @PostMapping(value = "/tokens")
    public ResponseEntity<Token> createToken(@Valid @RequestBody TokenModel tokenModel) {
        Token token = tokenModel.convertToEntity();
        return new ResponseEntity<>(tokenProcessingService.createToken(token), HttpStatus.CREATED);
    }

    /**
     * For updating a existing Token
     *
     * @param tokenId token identifier
     * @param tokenModel token model instance to be updated
     */
    @PutMapping(value = "/tokens/{tokenId}")
    public ResponseEntity<Token> updateToken(@PathVariable(value = "tokenId") Integer tokenId,
                            @Valid @RequestBody TokenModel tokenModel) {
        Token token = tokenModel.convertToEntity();
        tokenProcessingService.updateToken(tokenId, token);
        return new ResponseEntity<>(tokenProcessingService.updateToken(tokenId, token), HttpStatus.OK);
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
    @PutMapping(value = "/branch/{branchId}/tokens/{tokenId}/token-service")
    public ResponseEntity<String> processToken(@PathVariable(value = "branchId") Integer branchId,
                             @PathVariable(value = "tokenId") Integer tokenId,
                             @Valid @RequestBody TokenProcessParameters tokenProcessParameters) {
        Token token = tokenProcessingService.getToken(tokenId);
        tokenProcessingService.processToken(branchId, token, tokenProcessParameters.getComments(),
                tokenProcessParameters.getEmployee().convertToEntity());
        return new ResponseEntity<>("Token processing complete", HttpStatus.OK);
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
    @PutMapping(value = "/branch/{branchId}/tokens/{tokenId}/counter")
    public ResponseEntity<String> assignCounter(@PathVariable(value = "branchId") Integer branchId,
                             @PathVariable(value = "tokenId") Integer tokenId) {
        tokenProcessingService.assignCounter(tokenId, branchId);
        return new ResponseEntity<>("Token assignment complete", HttpStatus.OK);
    }
}
