package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.Counter;
import com.abc.bank.abc.Models.Employee;
import com.abc.bank.abc.Models.Token;
import com.abc.bank.abc.Services.BranchService;
import com.abc.bank.abc.Services.CounterService;
import com.abc.bank.abc.Services.ServicesSevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Counter", description = "Operations pertaining to the counters of a bank branch")
@RestController
public class CounterController {

    @Autowired
    BranchService branchService;

    @Autowired
    CounterService counterService;

    @Autowired
    ServicesSevice servicesSevice;

    /**
     * For getting all counters in a specific branch
     *
     * @param branchId
     * @return list of counters
     */
    @ApiOperation(value = "View all counters of a branch")
    @GetMapping("/branches/{id}/counters")
    public List<Counter> getBranchCounters(@PathVariable(value = "id") Integer branchId) {
        return branchService.getCounters(branchId);
    }

    /**
     * For creating a new counter in a branch
     *
     * @param branchId
     * @param counter
     * @return new counter instance
     */
    @ApiOperation(value = "creates a counter")
    @PostMapping("/branches/{id}/counters")
    public Counter createCounter(@PathVariable(value = "id") Integer branchId, @RequestBody Counter counter) {
        return counterService.createNewCounter(counter);
    }

    /**
     * For getting a specific counter in a branch with all the service steps it currently serves
     *
     * @param branchId
     * @param counterNumber
     * @return counter instance if exists
     */
    @ApiOperation(value = "View a particular counter of a branch")
    @GetMapping("/branches/{id}/counters/{counterId}")
    public Counter getBranchCounter(@PathVariable(value = "id") Integer branchId, @PathVariable(value = "counterId") Integer counterNumber) {
        return branchService.getCounter(branchId, counterNumber);
    }

    /**
     * For getting a specific counter in a branch with all the service steps it currently serves
     *
     * @param branchId
     * @param counter
     * @return counter instance if exists
     */
    @ApiOperation(value = "Updates a counter")
    @PutMapping("/branches/{id}/counters/")
    public void updateBranchCounter(@PathVariable(value = "id") Integer branchId, @RequestBody Counter counter) {
        counterService.updateCounter(counter);
    }

    @ApiOperation(value = "Adds a service to list of services currently served by a counter")
    @PutMapping("/counter/{counterId}/service/{serviceId}")
    public void addServiceToCounter(@PathVariable(value = "counterId") Integer counterId,
                                    @PathVariable(value = "serviceId") Integer serviceId) {
        BankingService bankingService = servicesSevice.getService(serviceId);
        counterService.addServiceToCounter(counterId, bankingService);
    }

    @ApiOperation(value = "Get list of tokens currently assigned to a particular counter")
    @GetMapping("/branches/{id}/counters/{counterId}/assignedTokens")
    public List<Token> getAssignedTokensToCounter(@PathVariable(value = "id") Integer branchId, @PathVariable(value = "counterId") Integer counterNumber) {
        return counterService.getAssignedTokens(counterNumber);
    }

    @ApiOperation(value = "Get the token which is to be picked for processing")
    @GetMapping("/counters/{counterId}/token_to_process")
    public Token getToBeProcessedToken(@PathVariable(value = "counterId") Integer counterId) {
        return counterService.getNextToken(counterId);
    }

    @ApiOperation(value = "Picks the token and changes the status to in process")
    @PutMapping("/counters/{counterId}/token/{tokenId}/pick_token")
    public void pickToken(@PathVariable(value = "counterId") Integer counterId, @PathVariable(value = "tokenId") Integer tokenId ) {
        counterService.pickToken(counterId, tokenId);
    }

    @ApiOperation(value = "Processes the token and adds comments")
    @PutMapping("/counters/{counterId}/token/{tokenId}/process_token")
    public void processToken(@PathVariable(value = "counterId") Integer counterId,
                             @PathVariable(value = "tokenId") Integer tokenId,
                             @RequestBody String actionOrComments, @RequestBody Employee employee) {
        counterService.processToken(counterId, tokenId, actionOrComments, employee);
    }

    @ApiOperation(value = "Completes the token and assign the token to another counter for service if applicable")
    @PutMapping("/branch/{branchId}/counters/{counterId}/token/{tokenId}/complete_token")
    public void completeToken(@PathVariable(value = "branchId") Integer branchId,
            @PathVariable(value = "counterId") Integer counterId,
                             @PathVariable(value = "tokenId") Integer tokenId,
                             @RequestBody String actionOrComments, @RequestBody Employee employee) {
        counterService.completeOrForwardToken(branchId, counterId, tokenId, actionOrComments, employee);
    }
}
