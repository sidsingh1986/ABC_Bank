package com.abc.bank.abc.controllers;

import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.viewmodels.CustomerModel;
import com.abc.bank.abc.datamodels.*;
import com.abc.bank.abc.services.CustomerManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value = "customer", description = "Operations pertaining to the customer management")
@RequestMapping("/customer-management")
@RestController
public class CustomerManagementController {

    @Autowired
    CustomerManagementService customerManagmentService;

    /**
     * For creating a new Customer
     *
     * @param customerModel customer model instance to be created
     * @return created customer instance
     */
    @ApiOperation(value = "Create a new Customer")
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerModel customerModel) {
        Customer customer = customerModel.convertToEntity();
        return new ResponseEntity<>(customerManagmentService.createCustomer(customer), HttpStatus.CREATED);
    }

    /**
     * For getting the list of all customers in the system
     *
     * @return list of all customers
     */
    @ApiOperation(value = "View all customers")
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerManagmentService.getAllCustomer();
        if (customers == null) {
            customers = new ArrayList<>();
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * For getting a particular Customer
     *
     * @param customerId customer identifier
     * @return customer with customer Id
     * @throws ResourceNotFoundException if the Customer for the id is not found
     */
    @ApiOperation(value = "Gets a particular customer")
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable(value = "customerId") Integer customerId) {
        return new ResponseEntity<>(customerManagmentService.getCustomer(customerId), HttpStatus.OK);
    }

    /**
     * For deleting a particular Customer
     *
     * @param customerId customer identifer
     */
    @ApiOperation(value = "Deletes a particular customer")
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(value = "customerId") Integer customerId) {
        customerManagmentService.deleteCustomer(customerId);
        return new ResponseEntity<>("Customer deletion successful", HttpStatus.OK);
    }

    /**
     * For updating a Customer or creating it if the Customer does not exists
     *
     * @param customerModel customer model instance to be updated
     */
    @ApiOperation(value = "Updates a particular customer")
    @PutMapping("/customers/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "customerId") Integer customerId,
                                                 @Valid @RequestBody CustomerModel customerModel) {
        Customer customer = customerModel.convertToEntity();
        return new ResponseEntity<>(customerManagmentService.updateCustomer(customerId, customer), HttpStatus.OK);
    }

}
