package com.abc.bank.abc.controllers;

import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.viewmodels.BankingServiceModel;
import com.abc.bank.abc.viewmodels.CounterModel;
import com.abc.bank.abc.viewmodels.TokenModel;
import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.Counter;
import com.abc.bank.abc.datamodels.Token;
import com.abc.bank.abc.services.CounterService;
import com.abc.bank.abc.services.TokenProcessingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value = "Counter", description = "Operations pertaining to the counters of a bank branch")
@RestController
public class CounterController {

    @Autowired
    CounterService counterService;

    /**
     * For creating a new counter
     *
     * @param counterModel counter model instance to be created
     * @return created counter instance
     */
    @ApiOperation(value = "creates a counter")
    @PostMapping("/counters")
    public ResponseEntity<Counter> createCounter(@Valid @RequestBody CounterModel counterModel) {
        Counter counter = counterModel.convertToEntity();
        return new ResponseEntity<>(counterService.createNewCounter(counter), HttpStatus.OK);
    }

    /**
     * For getting the list of all counters
     *
     * @return list of all counters
     */
    @ApiOperation(value = "Gets all counters")
    @GetMapping("/counters")
    public ResponseEntity<List<Counter>> getCounters() {
        List<Counter> counters = counterService.getAllCounters();
        return new ResponseEntity<>(counters, HttpStatus.OK);
    }

    /**
     * For getting a particular counter
     *
     * @param counterId counter identifier
     * @return counter with the counter id
     * @throws ResourceNotFoundException if the counter with passed id is not found
     */
    @ApiOperation(value = "Gets a particular counter")
    @GetMapping("/counters/{id}")
    public ResponseEntity<Counter> getCounter(@PathVariable("id") Integer counterId) {
        return new ResponseEntity<>(counterService.getCounter(counterId), HttpStatus.OK);
    }

    /**
     * For updating a counter and creates it if the counter does not exists
     *
     * @param counterModel counter model instance to be updated
     */
    @ApiOperation(value = "Updates a counter")
    @PutMapping("/counters/{counterId}")
    public ResponseEntity<Counter> updateCounter(@PathVariable("counterId") Integer counterId,
                                                          @Valid @RequestBody CounterModel counterModel) {
        Counter counter = counterModel.convertToEntity();
        return new ResponseEntity<>(counterService.updateCounter(counterId, counter), HttpStatus.OK);
    }

    /**
     * For adding a services to services served by  a counter
     *
     * @param counterId counter identifier
     * @param bankingServiceModel banking service model instance
     */
    @ApiOperation(value = "Adds a service to list of services currently served by a counter")
    @PutMapping("/counters/{counterId}/services")
    public ResponseEntity<HttpStatus> addServiceToCounter(@PathVariable(value = "counterId") Integer counterId,
                                    @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        counterService.addServiceToCounter(counterId, bankingService);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * For getting the list of services currently served by a counter
     *
     * @param counterId counter identifier
     * @return  list of banking services served by a particular counter
     */
    @ApiOperation(value = "Adds a service to list of services currently served by a counter")
    @GetMapping("/counters/{counterId}/services")
    public ResponseEntity<List<BankingService>> getServicesOfCounter(@PathVariable(value = "counterId") Integer counterId) {
        List<BankingService> bankingServices = counterService.listServicesOfferedByCounter(counterId);

        if (bankingServices == null) {
            bankingServices = new ArrayList<>();
        }
        return new ResponseEntity<>(bankingServices, HttpStatus.OK);
    }

    /**
     * For removing a service from the services currently served by a counter
     *
     * @param counterId counter identifier
     * @param bankingServiceModel banking service model instance
     */
    @ApiOperation(value = "For removing a service currently served by a counter")
    @DeleteMapping("/counters/{counterId}/services/{serviceId}")
    public ResponseEntity<HttpStatus> deleteServiceFromCounter(@PathVariable(value = "counterId") Integer counterId, @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        counterService.removeServiceFromCounter(counterId, bankingService);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * For viewing a service currently served by a counter
     *
     * @param counterId counter identifier
     * @param serviceId banking service identifier
     * @return banking service
     * @throws ResourceNotFoundException if the service is not served by counter
     */
    @ApiOperation(value = "Gets a service currently served by counter")
    @GetMapping("/counters/{counterId}/services/{serviceId}")
    public ResponseEntity<BankingService> getServiceOfCounter(@PathVariable(value = "counterId") Integer counterId,
                                                   @PathVariable(value = "serviceId") Integer serviceId) {
        return new ResponseEntity<>(counterService.getServiceOfferedByCounter(counterId, serviceId), HttpStatus.OK);
    }

    /**
     * For viewing the tokens currently assigned to counter
     *
     * @param counterNumber counter identifier
     * @return list of token
     */
    @ApiOperation(value = "Get list of tokens currently assigned to a particular counter")
    @GetMapping("/counters/{counterId}/tokens")
    public ResponseEntity<List<Token>> getAssignedTokensToCounter(@PathVariable(value = "counterId") Integer counterNumber) {
        List<Token> tokens = counterService.getAssignedTokens(counterNumber);

        if (tokens == null) {
            tokens = new ArrayList<>();
        }
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    /**
     * For getting the next token which will be processed by counter. It changes the status of
     * token to IN_PROCESS.
     *
     * @param counterId counter identifier
     * @return Token to process
     */
    @ApiOperation(value = "Get the token which is to be picked for processing")
    @PutMapping("/counters/{counterId}/next-token")
    public ResponseEntity<Token> getToBeProcessedToken(@PathVariable(value = "counterId") Integer counterId) {
        Token token = counterService.getNextToken(counterId);
        Token updatedToken = counterService.pickToken(token);
        return new ResponseEntity<>(updatedToken, HttpStatus.OK);
    }

    /**
     * For getting the token which is currently getting processed by counter.
     *
     * @param counterId counter identifier
     * @return Token which is getting processed
     */
    @ApiOperation(value = "Get the token which is currently getting processed")
    @PutMapping("/counters/{counterId}/current-token")
    public ResponseEntity<Token> getCurrentToken(@PathVariable(value = "counterId") Integer counterId) {
        Token token = counterService.getCurrentToken(counterId);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @ApiOperation(value = "Get the next token highest order token for the counter")
    @PutMapping("/counters/{counterId}/token")
    public ResponseEntity<Token> pickNextToken(@PathVariable(value = "counterId") Integer counterId) {
        Token token = counterService.pickNextToken(counterId);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
