package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
