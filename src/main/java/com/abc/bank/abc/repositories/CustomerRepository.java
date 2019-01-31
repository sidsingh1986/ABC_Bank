package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
