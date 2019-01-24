package com.abc.bank.abc.Services;

import com.abc.bank.abc.DtoModels.BankingServiceDTO;
import com.abc.bank.abc.DtoModels.MultiCounterBankingServiceDTO;
import com.abc.bank.abc.DtoModels.ServicesPlaceholder;
import com.abc.bank.abc.Enums.CustomerType;
import com.abc.bank.abc.Enums.EmployeeRoles;
import com.abc.bank.abc.Enums.ServiceProcessingType;
import com.abc.bank.abc.Enums.TokenServiceStatus;
import com.abc.bank.abc.Models.*;
import com.abc.bank.abc.Repositories.TokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TokenProcessingServiceImplTest {

    @Mock
    TokenRepository tokenRepository;

    @InjectMocks
    TokenProcessingServiceImpl tokenProcessingService;

    @InjectMocks
    BranchServiceImpl branchService;

    private Token createTokenStub() {
        Token queryToken = new Token();
        queryToken.setId(1);
        List<ServicesPlaceholder> servicesPlaceholderList = new ArrayList<>();
        BankingServiceDTO bankingServiceDTO1 = new BankingServiceDTO();
        bankingServiceDTO1.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingServiceDTO1.setId(1);
        bankingServiceDTO1.setName("Service 1");

        ServicesPlaceholder servicesPlaceholder = new ServicesPlaceholder();
        servicesPlaceholder.setService(bankingServiceDTO1);
        servicesPlaceholder.setOrderOfService(1);
        servicesPlaceholder.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder);

        BankingServiceDTO bankingServiceDTO2 = new BankingServiceDTO();
        bankingServiceDTO2.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingServiceDTO2.setId(2);
        bankingServiceDTO2.setName("Service 2");

        MultiCounterBankingServiceDTO multiCounterBankingServiceDTO = new MultiCounterBankingServiceDTO();
        multiCounterBankingServiceDTO.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
        multiCounterBankingServiceDTO.setId(1);
        multiCounterBankingServiceDTO.setName("Multi counter service 1");
        multiCounterBankingServiceDTO.setBankingServices(Arrays.asList(bankingServiceDTO1, bankingServiceDTO2));

        ServicesPlaceholder servicesPlaceholder1 = new ServicesPlaceholder();
        servicesPlaceholder1.setService(multiCounterBankingServiceDTO);
        servicesPlaceholder1.setOrderOfService(2);
        servicesPlaceholder1.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder1);

        ServicesPlaceholder servicesPlaceholder2 = new ServicesPlaceholder();
        servicesPlaceholder2.setService(bankingServiceDTO2);
        servicesPlaceholder2.setOrderOfService(3);
        servicesPlaceholder2.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder2);

        queryToken.setBankingServicesPlaceholder(servicesPlaceholderList);

        when(tokenRepository.save(any(Token.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });

        Token fetchedToken = tokenProcessingService.createToken(queryToken);

        return fetchedToken;
    }

    private List<Counter> createStubCounterList() {
        List<Counter> counters = new ArrayList<>();

        Counter counter1 = new Counter();
        counter1.setId(1);
        counter1.setName("Counter 1");

        List<Token> tokens = new ArrayList<>();
        List<Token> tokens1 = new ArrayList<>();

        Token token1 = new Token();
        token1.setId(1);

        Token token2 = new Token();
        token2.setId(2);

        Token token3 = new Token();
        token3.setId(3);

        Token token4 = new Token();
        token4.setId(4);

        Token token5 = new Token();
        token5.setId(5);

        Token token6 = new Token();
        token6.setId(6);

        tokens.add(token1);
        tokens.add(token3);
        tokens.add(token5);
        tokens.add(token6);

        tokens1.add(token2);
        tokens1.add(token4);

        counter1.setTokens(tokens);

        Counter counter2 = new Counter();
        counter2.setId(2);
        counter2.setName("Counter 2");
        counter2.setTokens(tokens1);

        counters.add(counter1);
        counters.add(counter2);

        return counters;
    }

    @Test
    public void createToken() {

        Token queryToken = new Token();
        queryToken.setId(1);
        List<ServicesPlaceholder> servicesPlaceholderList = new ArrayList<>();
        BankingServiceDTO bankingServiceDTO1 = new BankingServiceDTO();
        bankingServiceDTO1.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingServiceDTO1.setId(1);
        bankingServiceDTO1.setName("Service 1");

        ServicesPlaceholder servicesPlaceholder = new ServicesPlaceholder();
        servicesPlaceholder.setService(bankingServiceDTO1);
        servicesPlaceholder.setOrderOfService(1);
        servicesPlaceholder.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder);

        BankingServiceDTO bankingServiceDTO2 = new BankingServiceDTO();
        bankingServiceDTO2.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingServiceDTO2.setId(2);
        bankingServiceDTO2.setName("Service 2");

        MultiCounterBankingServiceDTO multiCounterBankingServiceDTO = new MultiCounterBankingServiceDTO();
        multiCounterBankingServiceDTO.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
        multiCounterBankingServiceDTO.setId(1);
        multiCounterBankingServiceDTO.setName("Multi counter service 1");
        multiCounterBankingServiceDTO.setBankingServices(Arrays.asList(bankingServiceDTO1, bankingServiceDTO2));

        ServicesPlaceholder servicesPlaceholder1 = new ServicesPlaceholder();
        servicesPlaceholder1.setService(multiCounterBankingServiceDTO);
        servicesPlaceholder1.setOrderOfService(2);
        servicesPlaceholder1.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder1);

        ServicesPlaceholder servicesPlaceholder2 = new ServicesPlaceholder();
        servicesPlaceholder2.setService(bankingServiceDTO2);
        servicesPlaceholder2.setOrderOfService(3);
        servicesPlaceholder2.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder2);

        queryToken.setBankingServicesPlaceholder(servicesPlaceholderList);

        when(tokenRepository.save(any(Token.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });

        Token fetchedToken = tokenProcessingService.createToken(queryToken);

        assertEquals(1, fetchedToken.getId());

        assertEquals(2, fetchedToken.getTokenServices().size());

        assertEquals(1, fetchedToken.getTokenMultiCounterServices().size());

    }

    @Test
    public void assignCounter() {

        Token token = createTokenStub();
        List<Counter> counters = createStubCounterList();
        when(tokenRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(token));

        when(branchService.getCountersForService(anyInt(), anyInt(), any(CustomerType.class))).thenReturn(counters);

        tokenProcessingService.assignCounter(1,1);
    }

    @Test
    public void getToken() {

        Token token = createTokenStub();

        when(tokenRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(token));

        Token fetchedToken = tokenProcessingService.getToken(1);
        assertEquals(1, fetchedToken.getId());
        assertEquals(2, fetchedToken.getTokenServices().size());
        assertEquals(1, fetchedToken.getTokenMultiCounterServices().size());
    }

    @Test
    public void getTokens() {

        Token token = createTokenStub();
        Token token1 = new Token();
        token1.setId(2);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);
        tokens.add(token1);

        when(tokenRepository.findAll()).thenReturn(tokens);

        List<Token> fetchedTokenList = tokenProcessingService.getTokens();

        assertEquals(2, fetchedTokenList.size());
    }

    @Test
    public void updateToken() {

        Token token = new Token();
        token.setId(1);

        tokenProcessingService.updateToken(token);

        verify(tokenRepository, times(1)).save(token);
    }

    @Test
    public void deleteToken() {
        tokenProcessingService.deleteToken(1);

        verify(tokenRepository, times(1)).deleteById(1);

    }

    @Test
    public void pickToken() {
    }

    @Test
    public void processToken() {
    }
}