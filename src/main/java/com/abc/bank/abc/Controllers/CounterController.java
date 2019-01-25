package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.ViewModels.BankingServiceModel;
import com.abc.bank.abc.ViewModels.CounterModel;
import com.abc.bank.abc.ViewModels.TokenModel;
import com.abc.bank.abc.DataModels.BankingService;
import com.abc.bank.abc.DataModels.Counter;
import com.abc.bank.abc.DataModels.Token;
import com.abc.bank.abc.Services.CounterService;
import com.abc.bank.abc.Services.TokenProcessingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value = "Counter", description = "Operations pertaining to the counters of a bank branch")
@RestController
public class CounterController {

    @Autowired
    CounterService counterService;

    @Autowired
    TokenProcessingService tokenProcessingService;

    /**
     * For creating a new counter
     *
     * @param counterModel
     * @return created counter instance
     */
    @ApiOperation(value = "creates a counter")
    @PostMapping("/counters")
    public CounterModel createCounter(@Valid @RequestBody CounterModel counterModel) {
        Counter counter = counterModel.convertToEntity();
        return counterService.createNewCounter(counter).convertToDTO();
    }

    /**
     * For getting the list of all counters
     *
     * @return list of all counters
     */
    @ApiOperation(value = "Gets all counters")
    @GetMapping("/counters")
    public List<CounterModel> getCounters() {
        List<Counter> counters = counterService.getAllCounters();
        List<CounterModel> counterModels = new ArrayList<>();

        for(int index = 0; index < counters.size(); index++) {
            counterModels.add(counters.get(index).convertToDTO());
        }
        return counterModels;
    }

    /**
     * For getting a particular counter
     *
     * @param counterId
     * @return counter with the counter id
     * @throws com.abc.bank.abc.Exceptions.ResourceNotFoundException if the counter with passed id is not found
     */
    @ApiOperation(value = "Gets a particular counter")
    @GetMapping("/counters/{id}")
    public CounterModel getCounter(@PathVariable("id") Integer counterId) {
        return counterService.getCounter(counterId).convertToDTO();
    }

    /**
     * For updating a counter and creates it if the counter does not exists
     *
     * @param counterModel
     */
    @ApiOperation(value = "Updates a counter")
    @PutMapping("/counters")
    public void updateBranchCounter(@Valid @RequestBody CounterModel counterModel) {
        Counter counter = counterModel.convertToEntity();
        counterService.updateCounter(counter);
    }

    /**
     * For adding a services to services served by  a counter
     *
     * @param counterId
     * @param bankingServiceModel
     */
    @ApiOperation(value = "Adds a service to list of services currently served by a counter")
    @PutMapping("/counters/{counterId}/services")
    public void addServiceToCounter(@PathVariable(value = "counterId") Integer counterId,
                                    @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        counterService.addServiceToCounter(counterId, bankingService);
    }

    /**
     * For getting the list of services currently served by a counter
     *
     * @param counterId
     * @return  list of banking services served by a particular counter
     */
    @ApiOperation(value = "Adds a service to list of services currently served by a counter")
    @GetMapping("/counters/{counterId}/services")
    public List<BankingServiceModel> getServicesOfCounter(@PathVariable(value = "counterId") Integer counterId) {
        List<BankingService> bankingServices = counterService.listServicesOfferedByCounter(counterId);
        List<BankingServiceModel> bankingServiceRespons = new ArrayList<>();

        for (int index = 0; index < bankingServices.size(); index++) {
            bankingServiceRespons.add(bankingServices.get(index).convertToDTO());
        }
        return bankingServiceRespons;
    }

    /**
     * For removing a service from the services currently served by a counter
     *
     * @param counterId
     * @param bankingServiceModel
     */
    @ApiOperation(value = "For removing a service currently served by a counter")
    @DeleteMapping("/counters/{counterId}/services/{serviceId}")
    public void deleteServiceFromCounter(@PathVariable(value = "counterId") Integer counterId, @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        counterService.removeServiceFromCounter(counterId, bankingService);
    }

    /**
     * For viewing a service currently served by a counter
     *
     * @param counterId
     * @param serviceId
     * @return banking service
     */
    @ApiOperation(value = "Adds a service to list of services currently served by a counter")
    @GetMapping("/counters/{counterId}/services/{serviceId}")
    public BankingServiceModel getServiceOfCounter(@PathVariable(value = "counterId") Integer counterId,
                                                   @PathVariable(value = "serviceId") Integer serviceId) {
        return counterService.getServiceOfferedByCounter(counterId, serviceId).convertToDTO();
    }

    @ApiOperation(value = "Get list of tokens currently assigned to a particular counter")
    @GetMapping("/counters/{counterId}/tokens")
    public List<TokenModel> getAssignedTokensToCounter(@PathVariable(value = "counterId") Integer counterNumber) {
        List<Token> tokens = counterService.getAssignedTokens(counterNumber);
        List<TokenModel> tokenModels = new ArrayList<>();

        for(int index = 0; index < tokens.size();index++) {
            tokenModels.add(tokens.get(index).convertToDTO());
        }
        return tokenModels;
    }

    @ApiOperation(value = "Get the token which is to be picked for processing")
    @PutMapping("/counters/{counterId}/next-token")
    public TokenModel getToBeProcessedToken(@PathVariable(value = "counterId") Integer counterId) {
        Token token = counterService.getNextToken(counterId);
        Token updatedToken = tokenProcessingService.pickToken(token);
        return updatedToken.convertToDTO();
    }

    @ApiOperation(value = "Get the token which is currently getting processed")
    @PutMapping("/counters/{counterId}/current-token")
    public TokenModel getCurrentToken(@PathVariable(value = "counterId") Integer counterId) {
        Token token = counterService.getCurrentToken(counterId);
        return token.convertToDTO();
    }

}
