package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.Customer;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CustomerManagementService {

    /**
     * For creating a new Customer
     *
     * @param customer customer instance to be created
     * @return created customer instance
     */
    Customer createCustomer(Customer customer);

    /**
     * For getting a particular Customer
     *
     * @param customerId customer identifier
     * @return customer with customer Id
     * @throws ResourceNotFoundException if the Customer for the id is not found
     */
    Customer getCustomer(Integer customerId);

    /**
     * For getting the list of all customers in the system
     *
     * @return list of all customers
     */
    List<Customer> getAllCustomer();

    /**
     * For updating a Customer or creating it if the Customer does not exists
     *
     * @param customerId
     * @param customer customer instance to be updated
     */
    void updateCustomer(Integer customerId, Customer customer);

    /**
     * For deleting a particular Customer
     *
     * @param customerId customer identifer
     */
    void deleteCustomer(Integer customerId);
}
