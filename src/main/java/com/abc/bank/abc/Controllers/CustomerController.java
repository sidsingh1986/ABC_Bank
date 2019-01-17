package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.Models.*;
import com.abc.bank.abc.Services.CustomerService;
import com.abc.bank.abc.Services.TokenProcessingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "customer", description = "Operations pertaining to the customer")
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    TokenProcessingService tokenProcessingService;

    @ApiOperation(value = "issues token for a new customer along with creation of customer")
    @RequestMapping(value = "/branch/{branchId}/issueToken", method = RequestMethod.POST)
    public Token issueTokenForNewCustomer(@PathVariable(value = "branchId") int branchId,
                                                             @RequestBody CustomerWithServicesPlaceholder customerWithServicesPlaceholder) {
        Customer customer = customerService.createNewCustomer(customerWithServicesPlaceholder.getCustomer());
        Token token = tokenProcessingService.createToken(customer, customerWithServicesPlaceholder.getServicesPlaceholder(), branchId);
        return token;
    }

    @ApiOperation(value = "issues token for by fetching customer based on account number details ")
    @RequestMapping(value = "/branch/{branchId}/customer/{accountNumber}/issueToken", method = RequestMethod.POST)
    public Token issueTokenForCustomer(@PathVariable(value = "branchId") int branchId,
                                                       @PathVariable(value = "accountNumber") int accountNumber,
                                                       @RequestBody ServicesPlaceholder servicesPlaceholder) {
        Customer customer = customerService.getCustomer(accountNumber);
        Token token = tokenProcessingService.createToken(customer, servicesPlaceholder, branchId);
        return token;
    }

}
