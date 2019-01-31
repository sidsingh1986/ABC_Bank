package com.abc.bank.abc.controllers;

import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.viewmodels.EmployeeModel;
import com.abc.bank.abc.datamodels.Employee;
import com.abc.bank.abc.services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value = "Employee", description = "Operations pertaining to the Employees")
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * For creating a new Employee
     *
     * @param employeeModel employee model instance to be created
     * @return created employee instance
     */
    @ApiOperation(value = "Create a new employee")
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeModel employeeModel) {

        Employee employee = employeeModel.convertToEntity();
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    /**
     * For getting the list of all employees in the system
     *
     * @return list of all employees
     */
    @ApiOperation(value = "View all employees")
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.getEmployees();

        if (employees == null) {
            employees = new ArrayList<>();
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * For getting a particular Employee
     *
     * @param employeeId employee identifier
     * @return employee with employee Id
     * @throws ResourceNotFoundException if the Employee for the id is not found
     */
    @ApiOperation(value = "View a particular employee")
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable(value = "employeeId") Integer employeeId) {
        return new ResponseEntity<>(employeeService.getEmployee(employeeId), HttpStatus.OK);
    }

    /**
     * For updating a Employee or creating it if the Employee does not exists
     *
     * @param employeeModel employee model instance to be updated
     */
    @ApiOperation(value = "Updates a particular employee")
    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable(value = "employeeId") Integer employeeId,
                                                 @Valid @RequestBody EmployeeModel employeeModel) {
        employeeService.updateEmployee(employeeId, employeeModel.convertToEntity());
        return new ResponseEntity<>("Employee update successful", HttpStatus.OK);
    }

    /**
     * For deleting a particular Employee
     *
     * @param employeeId employee identifier
     */
    @ApiOperation(value = "Deletes a particular employee")
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(value = "employeeId") Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee delete successful", HttpStatus.OK);

    }
}
