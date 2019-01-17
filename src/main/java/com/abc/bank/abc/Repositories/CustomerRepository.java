package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.Models.Customer;
import com.abc.bank.abc.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
