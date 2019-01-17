package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.Customer;
import com.abc.bank.abc.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer createNewCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(int accountNumber) {
        return customerRepository.findById(accountNumber).get();
    }
}
