package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.DtoModels.CustomerDTO;
import com.abc.bank.abc.Models.*;
import com.abc.bank.abc.Services.CustomerManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value = "customer", description = "Operations pertaining to the customer")
@RequestMapping("/customer-management")
@RestController
public class CustomerManagementController {

    @Autowired
    CustomerManagementService customerManagmentService;

    @ApiOperation(value = "Create a new Customer")
    @PostMapping("/customers")
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerDTO.convertToEntity();
        return customerManagmentService.createCustomer(customer).convertToDTO();
    }

    @ApiOperation(value = "View all customers")
    @GetMapping("/customers")
    public List<CustomerDTO> getCustomers() {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        List<Customer> customers = customerManagmentService.getAllCustomer();

        for(int index = 0; index < customers.size(); index++) {
            customerDTOS.add(customers.get(index).convertToDTO());
        }
        return customerDTOS;
    }

    @ApiOperation(value = "Gets a particular customer")
    @GetMapping("/customers/{customerId}")
    public void getCustomer(@PathVariable(value = "customerId") Integer customerId) {
        customerManagmentService.getCustomer(customerId);
    }

    @ApiOperation(value = "Deletes a particular customer")
    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable(value = "customerId") Integer customerId) {
        customerManagmentService.deleteCustomer(customerId);
    }

    @ApiOperation(value = "Updates a particular customer")
    @PutMapping("/customers")
    public void updateCustomer( @Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerDTO.convertToEntity();
        customerManagmentService.updateCustomer(customer);
    }

}
