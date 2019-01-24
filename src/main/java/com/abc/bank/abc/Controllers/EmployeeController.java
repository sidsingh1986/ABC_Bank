package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.DtoModels.EmployeeDTO;
import com.abc.bank.abc.Models.Employee;
import com.abc.bank.abc.Services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Employee", description = "Operations pertaining to the Employees")
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @ApiOperation(value = "Create a new employee")
    @PostMapping("/employees")
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {

        Employee employee = employeeDTO.convertToEntity();
        return employeeService.createEmployee(employee).convertToDTO();
    }

    @ApiOperation(value = "View all employees")
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @ApiOperation(value = "View a particular employee")
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(value = "employeeId") Integer employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @ApiOperation(value = "Updates a particular employee")
    @PutMapping("/employees")
    public void updateEmployee(@Valid @RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
    }

    @ApiOperation(value = "Deletes a particular employee")
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(value = "employeeId") Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
