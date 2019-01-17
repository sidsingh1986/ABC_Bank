package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.Models.Token;
import com.abc.bank.abc.Repositories.TokenRepository;
import com.abc.bank.abc.Services.TokenProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TokenController {

    @Autowired
    TokenProcessingService tokenProcessingService;

    @Autowired
    TokenRepository tokenRepository;

    @RequestMapping(value = "/token/{tokenId}/completeToken", method = RequestMethod.POST)
    public void completeToken(@PathVariable(value = "tokenId") Integer tokenId) {

        Token token = tokenRepository.findById(tokenId).get();
        tokenProcessingService.completeToken(token);
    }

    @RequestMapping(value = "/token/{tokenId}/cancelToken", method = RequestMethod.POST)
    public void cancelToken(@PathVariable(value = "tokenId") Integer tokenId) {
        Token token = tokenRepository.findById(tokenId).get();
        tokenProcessingService.cancelToken(token);
    }
}
