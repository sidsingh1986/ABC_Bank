package com.abc.bank.abc.Services;

import com.abc.bank.abc.DataModels.Employee;
import com.abc.bank.abc.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface EmployeeService {

    /**
     * For getting a particular Employee
     *
     * @param employeeId employee identifier
     * @return employee with employee Id
     * @throws ResourceNotFoundException if the Employee for the id is not found
     */
    Employee getEmployee(Integer employeeId);

    /**
     * For getting the list of all employees in the system
     *
     * @return list of all employees
     */
    List<Employee> getEmployees();

    /**
     * For creating a new Employee
     *
     * @param employee employee instance to be created
     * @return created employee instance
     */
    Employee createEmployee(Employee employee);

    /**
     * For updating a Employee or creating it if the Employee does not exists
     *
     * @param employee employee instance to be updated
     */
    void updateEmployee(Employee employee);

    /**
     * For deleting a particular Employee
     *
     * @param employeeId employee identifier
     */
    void deleteEmployee(Integer employeeId);
}
