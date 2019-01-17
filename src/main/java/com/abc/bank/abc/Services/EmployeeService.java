package com.abc.bank.abc.Services;

import com.abc.bank.abc.Models.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getEmployee(Integer employeeId);

    List<Employee> getEmployees();

    Employee createEmployee(Employee employee);

    void updateEmployee(Employee employee);
}
