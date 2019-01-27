package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.Exceptions.ResourceNotFoundException;
import com.abc.bank.abc.ViewModels.EmployeeModel;
import com.abc.bank.abc.DataModels.Employee;
import com.abc.bank.abc.Services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public EmployeeModel createEmployee(@Valid @RequestBody EmployeeModel employeeModel) {

        Employee employee = employeeModel.convertToEntity();
        return employeeService.createEmployee(employee).convertToDTO();
    }

    /**
     * For getting the list of all employees in the system
     *
     * @return list of all employees
     */
    @ApiOperation(value = "View all employees")
    @GetMapping("/employees")
    public List<EmployeeModel> getEmployees() {
        List<Employee> employees = employeeService.getEmployees();
        List<EmployeeModel> employeeModels = new ArrayList<>();

        for(int index = 0; index < employees.size(); index++) {
            employeeModels.add(employees.get(index).convertToDTO());
        }
        return employeeModels;
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
    public EmployeeModel getEmployee(@PathVariable(value = "employeeId") Integer employeeId) {
        return employeeService.getEmployee(employeeId).convertToDTO();
    }

    /**
     * For updating a Employee or creating it if the Employee does not exists
     *
     * @param employeeModel employee model instance to be updated
     */
    @ApiOperation(value = "Updates a particular employee")
    @PutMapping("/employees")
    public void updateEmployee(@Valid @RequestBody EmployeeModel employeeModel) {
        employeeService.updateEmployee(employeeModel.convertToEntity());
    }

    /**
     * For deleting a particular Employee
     *
     * @param employeeId employee identifier
     */
    @ApiOperation(value = "Deletes a particular employee")
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(value = "employeeId") Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
