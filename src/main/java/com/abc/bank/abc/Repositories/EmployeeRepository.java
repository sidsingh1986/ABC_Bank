package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.DataModels.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "select * from Employee emp where emp.username = :username", nativeQuery = true)
    Employee findByUsername(String username);
}
