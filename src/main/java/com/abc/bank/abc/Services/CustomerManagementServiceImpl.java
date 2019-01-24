package com.abc.bank.abc.Services;

import com.abc.bank.abc.Exceptions.ResourceNotFoundException;
import com.abc.bank.abc.Models.Customer;
import com.abc.bank.abc.Repositories.CustomerRepository;
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
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        customerRepository.deleteById(customerId);
    }
}
