package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.Exceptions.ResourceNotFoundException;
import com.abc.bank.abc.ViewModels.CustomerModel;
import com.abc.bank.abc.DataModels.*;
import com.abc.bank.abc.Services.CustomerManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CustomerModel createCustomer(@Valid @RequestBody CustomerModel customerModel) {
        Customer customer = customerModel.convertToEntity();
        return customerManagmentService.createCustomer(customer).convertToDTO();
    }

    /**
     * For getting the list of all customers in the system
     *
     * @return list of all customers
     */
    @ApiOperation(value = "View all customers")
    @GetMapping("/customers")
    public List<CustomerModel> getCustomers() {
        List<CustomerModel> customerModels = new ArrayList<>();
        List<Customer> customers = customerManagmentService.getAllCustomer();

        for(int index = 0; index < customers.size(); index++) {
            customerModels.add(customers.get(index).convertToDTO());
        }
        return customerModels;
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
    public CustomerModel getCustomer(@PathVariable(value = "customerId") Integer customerId) {
        return customerManagmentService.getCustomer(customerId).convertToDTO();
    }

    /**
     * For deleting a particular Customer
     *
     * @param customerId customer identifer
     */
    @ApiOperation(value = "Deletes a particular customer")
    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable(value = "customerId") Integer customerId) {
        customerManagmentService.deleteCustomer(customerId);
    }

    /**
     * For updating a Customer or creating it if the Customer does not exists
     *
     * @param customerModel customer model instance to be updated
     */
    @ApiOperation(value = "Updates a particular customer")
    @PutMapping("/customers")
    public void updateCustomer( @Valid @RequestBody CustomerModel customerModel) {
        Customer customer = customerModel.convertToEntity();
        customerManagmentService.updateCustomer(customer);
    }

}
