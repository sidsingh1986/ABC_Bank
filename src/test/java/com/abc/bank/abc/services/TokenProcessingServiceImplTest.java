package com.abc.bank.abc.services;

import com.abc.bank.abc.enums.TokenStatus;
import com.abc.bank.abc.viewmodels.BankingServiceModel;
import com.abc.bank.abc.viewmodels.MultiCounterBankingServiceModel;
import com.abc.bank.abc.viewmodels.ServicesPlaceholder;
import com.abc.bank.abc.enums.CustomerType;
import com.abc.bank.abc.enums.ServiceProcessingType;
import com.abc.bank.abc.enums.TokenServiceStatus;
import com.abc.bank.abc.datamodels.*;
import com.abc.bank.abc.repositories.TokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TokenProcessingServiceImplTest {

    @Mock
    TokenRepository tokenRepository;

    @InjectMocks
    TokenProcessingServiceImpl tokenProcessingService;

    @Mock
    TokenProcessingStepsServiceImpl tokenProcessingStepsService;

    @Mock
    BranchServiceImpl branchService;

    @Mock
    TokenServicesService tokenServicesService;

    @Mock
    TokenMultiCounterServicesService tokenMultiCounterServicesService;

    @Mock
    CounterServiceImpl counterService;

    private Token createTokenStub() {
        Token queryToken = new Token();
        queryToken.setId(1);
        List<ServicesPlaceholder> servicesPlaceholderList = new ArrayList<>();
        BankingServiceModel bankingServiceModel1 = new BankingServiceModel();
        bankingServiceModel1.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingServiceModel1.setId(1);
        bankingServiceModel1.setName("Service 1");

        ServicesPlaceholder servicesPlaceholder = new ServicesPlaceholder();
        servicesPlaceholder.setService(bankingServiceModel1);
        servicesPlaceholder.setOrderOfService(1);
        servicesPlaceholder.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder);

        BankingServiceModel bankingServiceModel2 = new BankingServiceModel();
        bankingServiceModel2.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingServiceModel2.setId(2);
        bankingServiceModel2.setName("Service 2");

        MultiCounterBankingServiceModel multiCounterBankingServiceModel = new MultiCounterBankingServiceModel();
        multiCounterBankingServiceModel.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
        multiCounterBankingServiceModel.setId(1);
        multiCounterBankingServiceModel.setName("Multi counter service 1");
        multiCounterBankingServiceModel.setBankingServices(Arrays.asList(bankingServiceModel1, bankingServiceModel2));

        ServicesPlaceholder servicesPlaceholder1 = new ServicesPlaceholder();
        servicesPlaceholder1.setService(multiCounterBankingServiceModel);
        servicesPlaceholder1.setOrderOfService(2);
        servicesPlaceholder1.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder1);

        ServicesPlaceholder servicesPlaceholder2 = new ServicesPlaceholder();
        servicesPlaceholder2.setService(bankingServiceModel2);
        servicesPlaceholder2.setOrderOfService(3);
        servicesPlaceholder2.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder2);

        queryToken.setBankingServicesPlaceholder(servicesPlaceholderList);

        Customer customer = new Customer();
        customer.setCustomerType(CustomerType.REGULAR);

        queryToken.setCustomer(customer);

        when(tokenServicesService.createTokenService(any(TokenService.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });

        when(tokenMultiCounterServicesService.createTokenMultiCounterService(any(TokenMultiCounterService.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });

        when(tokenRepository.save(any(Token.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });

        Token fetchedToken = tokenProcessingService.createToken(queryToken);


        return fetchedToken;
    }

    private Token createMultiCounterTokenStub() {
        Token queryToken = new Token();
        queryToken.setId(1);
        List<ServicesPlaceholder> servicesPlaceholderList = new ArrayList<>();
        BankingServiceModel bankingServiceModel1 = new BankingServiceModel();
        bankingServiceModel1.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingServiceModel1.setId(1);
        bankingServiceModel1.setName("Service 1");

        ServicesPlaceholder servicesPlaceholder = new ServicesPlaceholder();
        servicesPlaceholder.setService(bankingServiceModel1);
        servicesPlaceholder.setOrderOfService(2);
        servicesPlaceholder.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder);

        BankingServiceModel bankingServiceModel2 = new BankingServiceModel();
        bankingServiceModel2.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingServiceModel2.setId(2);
        bankingServiceModel2.setName("Service 2");

        MultiCounterBankingServiceModel multiCounterBankingServiceModel = new MultiCounterBankingServiceModel();
        multiCounterBankingServiceModel.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
        multiCounterBankingServiceModel.setId(1);
        multiCounterBankingServiceModel.setName("Multi counter service 1");
        multiCounterBankingServiceModel.setBankingServices(Arrays.asList(bankingServiceModel1, bankingServiceModel2));

        ServicesPlaceholder servicesPlaceholder1 = new ServicesPlaceholder();
        servicesPlaceholder1.setService(multiCounterBankingServiceModel);
        servicesPlaceholder1.setOrderOfService(1);
        servicesPlaceholder1.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder1);

        ServicesPlaceholder servicesPlaceholder2 = new ServicesPlaceholder();
        servicesPlaceholder2.setService(bankingServiceModel2);
        servicesPlaceholder2.setOrderOfService(3);
        servicesPlaceholder2.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder2);

        queryToken.setBankingServicesPlaceholder(servicesPlaceholderList);

        Customer customer = new Customer();
        customer.setCustomerType(CustomerType.REGULAR);

        queryToken.setCustomer(customer);

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
        counter1.setCustomerType(CustomerType.REGULAR);

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
        counter2.setCustomerType(CustomerType.REGULAR);

        counters.add(counter1);
        counters.add(counter2);

        return counters;
    }

    @Test
    public void createToken() {

        Token queryToken = new Token();
        queryToken.setId(1);
        List<ServicesPlaceholder> servicesPlaceholderList = new ArrayList<>();
        BankingServiceModel bankingServiceModel1 = new BankingServiceModel();
        bankingServiceModel1.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingServiceModel1.setId(1);
        bankingServiceModel1.setName("Service 1");

        ServicesPlaceholder servicesPlaceholder = new ServicesPlaceholder();
        servicesPlaceholder.setService(bankingServiceModel1);
        servicesPlaceholder.setOrderOfService(1);
        servicesPlaceholder.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder);

        BankingServiceModel bankingServiceModel2 = new BankingServiceModel();
        bankingServiceModel2.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingServiceModel2.setId(2);
        bankingServiceModel2.setName("Service 2");

        MultiCounterBankingServiceModel multiCounterBankingServiceModel = new MultiCounterBankingServiceModel();
        multiCounterBankingServiceModel.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
        multiCounterBankingServiceModel.setId(1);
        multiCounterBankingServiceModel.setName("Multi counter service 1");
        multiCounterBankingServiceModel.setBankingServices(Arrays.asList(bankingServiceModel1, bankingServiceModel2));

        ServicesPlaceholder servicesPlaceholder1 = new ServicesPlaceholder();
        servicesPlaceholder1.setService(multiCounterBankingServiceModel);
        servicesPlaceholder1.setOrderOfService(2);
        servicesPlaceholder1.setTokenServiceStatus(TokenServiceStatus.CREATED);
        servicesPlaceholderList.add(servicesPlaceholder1);

        ServicesPlaceholder servicesPlaceholder2 = new ServicesPlaceholder();
        servicesPlaceholder2.setService(bankingServiceModel2);
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

        BankingService bankingService = new BankingService();
        bankingService.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingService.setId(1);
        bankingService.setName("Service 1");

        TokenService tokenService = new TokenService();
        tokenService.setProcessingOrder(1);
        tokenService.setService(bankingService);
        tokenService.setToken(token);

        TokenService updatedTokenService = new TokenService();
        updatedTokenService.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        updatedTokenService.setToken(tokenService.getToken());
        updatedTokenService.setProcessingOrder(tokenService.getProcessingOrder());
        updatedTokenService.setService(tokenService.getService());

        when(tokenRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(token));

        when(counterService.getCountersForService(1, 1, CustomerType.REGULAR)).thenReturn(counters);

        when(tokenServicesService.getHighestOrderPendingTokenService(1)).thenReturn(tokenService);

        when(tokenMultiCounterServicesService.getHighestOrderPendingMultiCounterService(1)).thenReturn(null);

        when(tokenServicesService.updateTokenServiceStatus(tokenService, TokenServiceStatus.COUNTER_ASSIGNED)).thenReturn(updatedTokenService);

        Token updatedToken = tokenProcessingService.assignCounter(1,1);

        assertEquals(1, updatedToken.getId());

        assertEquals(TokenStatus.COUNTER_ASSIGNED, updatedToken.getStatus());

    }

    @Test
    public void assignCounterMultiCounterService() {

        Token token = createMultiCounterTokenStub();
        List<Counter> counters = createStubCounterList();

        MultiCounterBankingService multiCounterBankingService = new MultiCounterBankingService();
        multiCounterBankingService.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
        multiCounterBankingService.setId(1);
        multiCounterBankingService.setName("Multi Counter Service 1");

        BankingService bankingService = new BankingService();
        bankingService.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingService.setId(1);
        bankingService.setName("Service 1");

        List<BankingService> bankingServices = new ArrayList<>();
        bankingServices.add(bankingService);

        multiCounterBankingService.setBankingServices(bankingServices);

        TokenMultiCounterService tokenMultiCounterService = new TokenMultiCounterService();
        tokenMultiCounterService.setService(multiCounterBankingService);
        tokenMultiCounterService.setProcessingOrder(1);
        tokenMultiCounterService.setToken(token);
        tokenMultiCounterService.setId(1);

        when(tokenRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(token));

        when(counterService.getCountersForService(1, 1, CustomerType.REGULAR)).thenReturn(counters);

        when(tokenServicesService.getHighestOrderPendingTokenService(1)).thenReturn(null);

        when(tokenMultiCounterServicesService.getHighestOrderPendingMultiCounterService(1)).thenReturn(tokenMultiCounterService);

        Token updatedToken = tokenProcessingService.assignCounter(1,1);

        assertEquals(1, updatedToken.getId());

        assertEquals(TokenStatus.COUNTER_ASSIGNED, updatedToken.getStatus());
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
        token.setStatus(TokenStatus.ISSUED);

        when(tokenRepository.findById(1)).thenReturn(Optional.of(token));
        when(tokenRepository.save(any(Token.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });

        Token updatedToken = tokenProcessingService.updateToken(1, token);

        assertEquals(1, updatedToken.getId());
        assertEquals(TokenStatus.ISSUED, updatedToken.getStatus());
    }

    @Test
    public void deleteToken() {
        tokenProcessingService.deleteToken(1);

        verify(tokenRepository, times(1)).deleteById(1);

    }

    @Test
    public void pickToken() {

        Token token = createTokenStub();
        List<Counter> counters = createStubCounterList();

        BankingService bankingService = new BankingService();
        bankingService.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        bankingService.setId(1);
        bankingService.setName("Service 1");

        TokenService tokenService = new TokenService();
        tokenService.setProcessingOrder(1);
        tokenService.setService(bankingService);
        tokenService.setToken(token);

        TokenService updatedTokenService = new TokenService();
        updatedTokenService.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        updatedTokenService.setToken(tokenService.getToken());
        updatedTokenService.setProcessingOrder(tokenService.getProcessingOrder());
        updatedTokenService.setService(tokenService.getService());

        when(tokenRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(token));

        when(counterService.getCountersForService(1, 1, CustomerType.REGULAR)).thenReturn(counters);

        when(tokenServicesService.getHighestOrderPendingTokenService(1)).thenReturn(tokenService);

        when(tokenMultiCounterServicesService.getHighestOrderPendingMultiCounterService(1)).thenReturn(null);

        when(tokenServicesService.updateTokenServiceStatus(tokenService, TokenServiceStatus.COUNTER_ASSIGNED)).thenReturn(updatedTokenService);

        Token updatedToken = tokenProcessingService.assignCounter(1,1);

        TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
        tokenProcessingSteps.setServiceId(1);
        tokenProcessingSteps.setService(updatedToken.getTokenServices().get(0).getService());
        tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        tokenProcessingSteps.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        tokenProcessingSteps.setToken(updatedToken);
        tokenProcessingSteps.setId(1);
        when(tokenProcessingStepsService.getStatusTokenProcessingStepForToken(1, TokenServiceStatus.COUNTER_ASSIGNED)).thenReturn(tokenProcessingSteps);

        Token fetchedToken = tokenProcessingService.pickToken(updatedToken);

        assertEquals(TokenStatus.IN_PROCESS, fetchedToken.getStatus());
    }

    @Test
    public void pickTokenMultiCounterService() {

        Token token = createMultiCounterTokenStub();

        TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
        tokenProcessingSteps.setServiceId(1);
        tokenProcessingSteps.setId(1);
        tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
        tokenProcessingSteps.setStatus(TokenServiceStatus.COUNTER_ASSIGNED);
        tokenProcessingSteps.setToken(token);

        TokenProcessingSteps tokenProcessingSteps1 = new TokenProcessingSteps();
        tokenProcessingSteps1.setServiceId(1);
        tokenProcessingSteps1.setId(2);
        tokenProcessingSteps1.setServiceProcessingType(ServiceProcessingType.MULTI_COUNTER);
        tokenProcessingSteps1.setToken(token);

        List<TokenProcessingSteps> tokenProcessingStepsList = new ArrayList<>();
        tokenProcessingStepsList.add(tokenProcessingSteps);
        tokenProcessingStepsList.add(tokenProcessingSteps1);

        when(tokenProcessingStepsService.getTokenProcessingStepsForToken(token.getId())).thenReturn(tokenProcessingStepsList);

        Token fetchedToken = tokenProcessingService.pickToken(token);

        assertEquals(TokenStatus.IN_PROCESS, fetchedToken.getStatus());
        assertEquals(TokenServiceStatus.IN_PROCESS, fetchedToken.getTokenMultiCounterServices().get(0).getStatus());
    }

    @Test
    public void processToken() {

        Token token = createTokenStub();

        TokenProcessingSteps tokenProcessingSteps = new TokenProcessingSteps();
        tokenProcessingSteps.setServiceId(1);
        tokenProcessingSteps.setId(1);
        tokenProcessingSteps.setServiceProcessingType(ServiceProcessingType.SINGLE_COUNTER);
        tokenProcessingSteps.setStatus(TokenServiceStatus.IN_PROCESS);
        tokenProcessingSteps.setToken(token);

        List<TokenProcessingSteps> tokenProcessingStepsList = new ArrayList<>();
        tokenProcessingStepsList.add(tokenProcessingSteps);

        when(tokenProcessingStepsService.getTokenProcessingStepsForToken(token.getId())).thenReturn(tokenProcessingStepsList);

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("testUser");

        TokenProcessingSteps tokenProcessingSteps1 = new TokenProcessingSteps();
        tokenProcessingSteps1.setId(tokenProcessingSteps.getId());
        tokenProcessingSteps1.setServiceId(tokenProcessingSteps.getServiceId());
        tokenProcessingSteps1.setServiceProcessingType(tokenProcessingSteps.getServiceProcessingType());
        tokenProcessingSteps1.setStatus(TokenServiceStatus.COMPLETED);
        tokenProcessingSteps1.setToken(tokenProcessingSteps.getToken());
        tokenProcessingSteps1.setEmployee(employee);
        tokenProcessingSteps1.setActionOrComments("test update");

        tokenProcessingService.processToken(1, token, "test update", employee);

        verify(tokenProcessingStepsService, times(1)).updateTokenProcessingStep(tokenProcessingSteps1);
    }
}