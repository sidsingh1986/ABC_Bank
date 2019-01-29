package com.abc.bank.abc.Services;

import com.abc.bank.abc.DataModels.Token;
import com.abc.bank.abc.DataModels.TokenProcessingSteps;
import com.abc.bank.abc.Enums.ServiceProcessingType;
import com.abc.bank.abc.Repositories.TokenProcessingStepsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TokenProcessingStepsServiceImplTest {

    @Mock
    TokenProcessingStepsRepository tokenProcessingStepsRepository;

    @InjectMocks
    TokenProcessingStepsServiceImpl tokenProcessingStepsService;

    @Test
    public void createTokenProcessingStep() {
        TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();

        tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        tokenProcessingSteps.setServiceId(1);

        TokenProcessingSteps fetchedTokenProcessingSteps = new TokenProcessingSteps();
        fetchedTokenProcessingSteps.setServiceId(tokenProcessingSteps.getServiceId());
        fetchedTokenProcessingSteps.setId(1);
        fetchedTokenProcessingSteps.setServiceProcessingType(tokenProcessingSteps.getServiceProcessingType());

        when(tokenProcessingStepsRepository.save(tokenProcessingSteps)).thenReturn(fetchedTokenProcessingSteps);

        TokenProcessingSteps returnedTokenProcessingSteps = tokenProcessingStepsService.createTokenProcessingStep(tokenProcessingSteps);

        assertEquals(1, returnedTokenProcessingSteps.getServiceId());
        assertEquals(1, returnedTokenProcessingSteps.getId());
    }

    @Test
    public void getTokenProcessingStepsForToken() {

        Token token = new Token();
        token.setId(1);

        TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
        tokenProcessingSteps.setServiceId(1);
        tokenProcessingSteps.setId(1);
        tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        tokenProcessingSteps.setToken(token);

        TokenProcessingSteps tokenProcessingSteps1 = new TokenProcessingSteps();
        tokenProcessingSteps1.setServiceId(1);
        tokenProcessingSteps1.setId(2);
        tokenProcessingSteps1.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        tokenProcessingSteps1.setToken(token);

        List<TokenProcessingSteps> tokenProcessingStepsList = new ArrayList<>();
        tokenProcessingStepsList.add(tokenProcessingSteps);
        tokenProcessingStepsList.add(tokenProcessingSteps1);

        when(tokenProcessingStepsRepository.findTokenProcessingStepsById(1)).thenReturn(tokenProcessingStepsList);

        List<TokenProcessingSteps> fethedTokenProcessingSteps = tokenProcessingStepsService.getTokenProcessingStepsForToken(1);

        assertEquals(2, fethedTokenProcessingSteps.size());
    }

    @Test
    public void updateTokenProcessingStep() {

        TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
        tokenProcessingSteps.setServiceId(1);
        tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);

        TokenProcessingSteps fetchedTokenProcessingSteps = new TokenProcessingSteps();
        fetchedTokenProcessingSteps.setServiceId(1);
        fetchedTokenProcessingSteps.setId(1);
        fetchedTokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);

        tokenProcessingStepsService.updateTokenProcessingStep(tokenProcessingSteps);

        verify(tokenProcessingStepsRepository, times(1)).save(tokenProcessingSteps);
    }

    @Test
    public void getTokenProcessingSteps() {

        TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
        tokenProcessingSteps.setServiceId(1);
        tokenProcessingSteps.setId(1);
        tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);

        when(tokenProcessingStepsRepository.findById(1)).thenReturn(java.util.Optional.of(tokenProcessingSteps));

        TokenProcessingSteps fetchedTokenProcessingSteps = tokenProcessingStepsService.getTokenProcessingSteps(1);

        assertEquals(1, fetchedTokenProcessingSteps.getId());
        assertEquals(1, fetchedTokenProcessingSteps.getServiceId());
    }
}