package com.abc.bank.abc.services;

import com.abc.bank.abc.datamodels.Employee;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Employee getEmployee(Integer employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        employee.setPasswordHash(bCryptPasswordEncoder.encode(employee.getPasswordHash()));
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Integer employeeId, Employee employee) {

        if (!employeeRepository.findById(employeeId).isPresent()) {
            throw new ResourceNotFoundException(employeeId, "Employee not found for updating");
        }

        if (employee == null) {
            throw new NullPointerException("The employee parameter can't be null");
        }
        employee.setId(employeeId);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
