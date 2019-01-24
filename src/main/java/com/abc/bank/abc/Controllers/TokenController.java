package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.DtoModels.TokenDTO;
import com.abc.bank.abc.DtoModels.TokenProcessParameters;
import com.abc.bank.abc.Models.Token;
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

    @GetMapping(value = "/tokens")
    public List<TokenDTO> getTokens() {

        List<Token> tokens = tokenProcessingService.getTokens();
        List<TokenDTO> tokenDTOS = new ArrayList<>();

        for (int index = 0; index < tokens.size(); index++) {
            tokenDTOS.add(tokens.get(index).convertToDTO());
        }
        return tokenDTOS;
    }

    @GetMapping(value = "/tokens/{id}")
    public TokenDTO getToken(@PathVariable(value = "id") Integer tokenId) {
        return tokenProcessingService.getToken(tokenId).convertToDTO();
    }

    @PostMapping(value = "/tokens")
    public TokenDTO createToken(@Valid @RequestBody TokenDTO tokenDTO) {
        Token token = tokenDTO.convertToEntity();
        return tokenProcessingService.createToken(token).convertToDTO();
    }

    @PutMapping(value = "/tokens")
    public void updateToken(@Valid @RequestBody TokenDTO tokenDTO) {
        Token token = tokenDTO.convertToEntity();
        tokenProcessingService.updateToken(token);
    }

    @PutMapping(value = "/branch/{branchId}/tokens/{tokenId}/process")
    public void processToken(@PathVariable(value = "branchId") Integer branchId,
                             @PathVariable(value = "tokenId") Integer tokenId,
                             @Valid @RequestBody TokenProcessParameters tokenProcessParameters) {
        Token token = tokenProcessingService.getToken(tokenId);
        tokenProcessingService.processToken(branchId, token, tokenProcessParameters.getComments(),
                tokenProcessParameters.getEmployee().convertToEntity());
    }

    @PutMapping(value = "/branch/{branchId}/tokens/{tokenId}/assign")
    public void assignCounter(@PathVariable(value = "branchId") Integer branchId,
                             @PathVariable(value = "tokenId") Integer tokenId) {
        tokenProcessingService.assignCounter(tokenId, branchId);
    }
}
