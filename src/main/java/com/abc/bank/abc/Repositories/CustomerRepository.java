package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.DataModels.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
