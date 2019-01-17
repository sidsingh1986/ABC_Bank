package com.abc.bank.abc.Services;

import com.abc.bank.abc.Enums.TokenServiceStatus;
import com.abc.bank.abc.Enums.TokenStatus;
import com.abc.bank.abc.Models.*;
import com.abc.bank.abc.Repositories.TokenRepository;
import com.abc.bank.abc.Repositories.TokenServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class TokenServiceServiceImpl implements TokenServiceService {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    TokenServiceRepository tokenServiceRepository;


    @Override
    public void completeTokenService(TokenService tokenService) {
        tokenService.setStatus(TokenServiceStatus.COMPLETED);
        tokenServiceRepository.save(tokenService);
    }

    @Override
    public void cancelTokenService(TokenService tokenService) {
        tokenService.setStatus(TokenServiceStatus.CANCELLED);
        tokenServiceRepository.save(tokenService);
    }

    @Override
    public TokenService getHighestOrderTokenService(Integer tokenId) {
        Token token = tokenRepository.findById(tokenId).get();
        if (token.getStatus().equals(TokenStatus.ISSUED)) {
            List<TokenService> tokenServices = token.getTokenServices();
            tokenServices.sort(Comparator.comparing(TokenService::getRequestOrder));
            Iterator<TokenService> iterator = tokenServices.iterator();

            while (iterator.hasNext()) {
                TokenService tokenService = iterator.next();
                if (tokenService.getStatus().equals(TokenServiceStatus.QUEUED)) {
                    return tokenService;
                }
            }
        } else {
            System.out.println("the token is either cancelled or completed");
        }

        return null;
    }

    @Override
    public TokenService getPendingTokenServiceBasedOnBankingService(Integer tokenId, BankingService bankingService) {
        Token token = tokenRepository.findById(tokenId).get();
        if (token.getStatus().equals(TokenStatus.ISSUED)) {
            List<TokenService> tokenServices = token.getTokenServices();
            Iterator<TokenService> iterator = tokenServices.iterator();

            while (iterator.hasNext()) {
                TokenService tokenService = iterator.next();
                if (tokenService.getStatus().equals(TokenServiceStatus.QUEUED)
                        && tokenService.getBankingService().getId() == bankingService.getId()) {
                    return tokenService;
                }
            }
        }
        return null;
    }

    @Override
    public List<TokenService> generateTokenServices(ServicesPlaceholder servicesPlaceholder) {
        List<MultiCounterBankingService> multiCounterBankingServices = servicesPlaceholder.getMultiCounterBankingServices();
        List<BankingService> bankingServices = servicesPlaceholder.getBankingServices();

        return  splitServicesAndPrioritize(multiCounterBankingServices, bankingServices);
    }

    @Override
    public void addActionOrComments(TokenService tokenService, String actionOrComments, Employee servedBy) {
        tokenService.setActionOrComments(actionOrComments);
        tokenService.setServedBy(servedBy);
        tokenServiceRepository.save(tokenService);
    }

    @Override
    public void setTokenStatusToProcess(TokenService tokenService) {
        tokenService.setStatus(TokenServiceStatus.IN_PROCESS);
        tokenServiceRepository.save(tokenService);
    }

    @Override
    public TokenService createTokenService(TokenService tokenService) {
        return tokenServiceRepository.save(tokenService);
    }

    @Override
    public void updateTokenService(TokenService tokenService) {
        tokenServiceRepository.save(tokenService);
    }

    @Override
    public TokenService getTokenService(Integer tokenServiceId) {
        return tokenServiceRepository.findById(tokenServiceId).get();
    }

    @Override
    public List<TokenService> getTokenServices() {
        return tokenServiceRepository.findAll();
    }

    private List<TokenService> splitServicesAndPrioritize(List<MultiCounterBankingService> multiCounterBankingServices, List<BankingService> bankingServices) {
        List<BankingService> cummilativeBankingServices = new ArrayList<>();
        for (int index = 0; index < multiCounterBankingServices.size(); index++) {
            cummilativeBankingServices.addAll(multiCounterBankingServices.get(index).getBankingServices());
        }
        cummilativeBankingServices.addAll(bankingServices);
        List<TokenService> tokenServices = new ArrayList<>();
        for (int index = 0; index < cummilativeBankingServices.size(); index++) {
            tokenServices.add(new TokenService());
            tokenServices.get(index).setBankingService(cummilativeBankingServices.get(index));
            tokenServices.get(index).setRequestOrder(index+1);
            tokenServices.get(index).setStatus(TokenServiceStatus.CREATED);
        }
        return tokenServices;
    }
}
