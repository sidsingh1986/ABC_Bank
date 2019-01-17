package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.Customer;
import com.abc.bank.abc.Models.Token;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {

    Customer createNewCustomer(Customer customer);

    Customer getCustomer(int accountNumber);
}
