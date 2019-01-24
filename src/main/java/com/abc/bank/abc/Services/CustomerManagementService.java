package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.Customer;
import com.abc.bank.abc.Models.Token;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerManagementService {

    Customer createCustomer(Customer customer);

    Customer getCustomer(Integer customerId);

    List<Customer> getAllCustomer();

    void updateCustomer(Customer customer);

    void deleteCustomer(Integer customerId);
}
