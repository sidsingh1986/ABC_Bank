package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.Models.Employee;
import com.abc.bank.abc.Services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Employee", description = "Operations pertaining to the Employees")
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @ApiOperation(value = "Create a new employee")
    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @ApiOperation(value = "View all employees")
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @ApiOperation(value = "View a particular employee")
    @GetMapping("/employees/{employeeId}")
    public Employee getService(@PathVariable(value = "employeeId") Integer employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @ApiOperation(value = "Updates a particular employee")
    @PutMapping("/employee")
    public void updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
    }
}
