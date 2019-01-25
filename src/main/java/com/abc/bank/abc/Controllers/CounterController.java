package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.DtoModels.BankingServiceDTO;
import com.abc.bank.abc.DtoModels.CounterDTO;
import com.abc.bank.abc.DtoModels.TokenDTO;
import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.Counter;
import com.abc.bank.abc.Models.Employee;
import com.abc.bank.abc.Models.Token;
import com.abc.bank.abc.Services.BranchService;
import com.abc.bank.abc.Services.CounterService;
import com.abc.bank.abc.Services.ServicesSevice;
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
     * For creating a new counter in a branch
     *
     * @param counterDTO
     * @return new counter instance
     */
    @ApiOperation(value = "creates a counter")
    @PostMapping("/counters")
    public CounterDTO createCounter(@Valid @RequestBody CounterDTO counterDTO) {
        Counter counter = counterDTO.convertToEntity();
        return counterService.createNewCounter(counter).convertToDTO();
    }

    @ApiOperation(value = "Gets all counters")
    @GetMapping("/counters")
    public List<CounterDTO> getCounters() {
        List<Counter> counters = counterService.getAllCounters();
        List<CounterDTO> counterDTOS = new ArrayList<>();

        for(int index = 0; index < counters.size(); index++) {
            counterDTOS.add(counters.get(index).convertToDTO());
        }
        return counterDTOS;
    }

    @ApiOperation(value = "Gets a particular counter")
    @GetMapping("/counters/{id}")
    public CounterDTO getCounter(@PathVariable("id") Integer counterId) {
        return counterService.getCounter(counterId).convertToDTO();
    }

    /**
     * For getting a specific counter in a branch with all the service steps it currently serves
     *
     * @param counterDTO
     * @return counter instance if exists
     */
    @ApiOperation(value = "Updates a counter")
    @PutMapping("/counters")
    public void updateBranchCounter(@Valid @RequestBody CounterDTO counterDTO) {
        Counter counter = counterDTO.convertToEntity();
        counterService.updateCounter(counter);
    }

    @ApiOperation(value = "Adds a service to list of services currently served by a counter")
    @PutMapping("/counters/{counterId}/services")
    public void addServiceToCounter(@PathVariable(value = "counterId") Integer counterId,
                                    @RequestBody  BankingServiceDTO bankingServiceDTO) {
        BankingService bankingService = bankingServiceDTO.convertToEntity();
        counterService.addServiceToCounter(counterId, bankingService);
    }

    @ApiOperation(value = "Adds a service to list of services currently served by a counter")
    @GetMapping("/counters/{counterId}/services")
    public List<BankingServiceDTO> getServicesOfCounter(@PathVariable(value = "counterId") Integer counterId) {
        List<BankingService> bankingServices = counterService.listServicesOfferedByCounter(counterId);
        List<BankingServiceDTO> bankingServiceDTOS = new ArrayList<>();

        for (int index = 0; index < bankingServices.size(); index++) {
            bankingServiceDTOS.add(bankingServices.get(index).convertToDTO());
        }
        return bankingServiceDTOS;
    }

    @ApiOperation(value = "Adds a service to list of services currently served by a counter")
    @DeleteMapping("/counters/{counterId}/services/{serviceId}")
    public void deleteServiceFromCounter(@PathVariable(value = "counterId") Integer counterId, @RequestBody BankingServiceDTO bankingServiceDTO) {
        BankingService bankingService = bankingServiceDTO.convertToEntity();
        counterService.removeServiceFromCounter(counterId, bankingService);
    }

    @ApiOperation(value = "Adds a service to list of services currently served by a counter")
    @GetMapping("/counters/{counterId}/services/{serviceId}")
    public BankingServiceDTO getServiceOfCounter(@PathVariable(value = "counterId") Integer counterId,
                                                 @PathVariable(value = "serviceId") Integer serviceId) {
        return counterService.getServiceOfferedByCounter(counterId, serviceId).convertToDTO();
    }

    @ApiOperation(value = "Get list of tokens currently assigned to a particular counter")
    @GetMapping("/counters/{counterId}/tokens")
    public List<TokenDTO> getAssignedTokensToCounter(@PathVariable(value = "counterId") Integer counterNumber) {
        List<Token> tokens = counterService.getAssignedTokens(counterNumber);
        List<TokenDTO> tokenDTOS = new ArrayList<>();

        for(int index = 0; index < tokens.size();index++) {
            tokenDTOS.add(tokens.get(index).convertToDTO());
        }
        return tokenDTOS;
    }

    @ApiOperation(value = "Get the token which is to be picked for processing")
    @PutMapping("/counters/{counterId}/next-token")
    public TokenDTO getToBeProcessedToken(@PathVariable(value = "counterId") Integer counterId) {
        Token token = counterService.getNextToken(counterId);
        Token updatedToken = tokenProcessingService.pickToken(token);
        return updatedToken.convertToDTO();
    }

    @ApiOperation(value = "Get the token which is currently getting processed")
    @PutMapping("/counters/{counterId}/current-token")
    public TokenDTO getCurrentToken(@PathVariable(value = "counterId") Integer counterId) {
        Token token = counterService.getCurrentToken(counterId);
        return token.convertToDTO();
    }

}
