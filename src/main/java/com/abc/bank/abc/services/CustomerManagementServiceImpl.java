package com.abc.bank.abc.services;

import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.datamodels.Customer;
import com.abc.bank.abc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Integer customerId) {
        Optional<Customer> optional = customerRepository.findById(customerId);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResourceNotFoundException(customerId, "customer not found");
        }
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Integer customerId, Customer customer) {

        if (!customerRepository.findById(customerId).isPresent()) {
            throw new ResourceNotFoundException(customerId, "The customer you are trying to update is not found");
        }
        customer.setId(customerId);
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        customerRepository.deleteById(customerId);
    }
}
