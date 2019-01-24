package com.abc.bank.abc.DtoModels;

import com.abc.bank.abc.Enums.EmployeeRoles;
import com.abc.bank.abc.Models.Employee;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EmployeeDTO {

    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    private String passwordHash;

    @NotNull
    @Size(min = 1, max = 16)
    private String phoneNumber;

    @NotNull
    private EmployeeRoles role;

    @NotNull
    private short enabled;

    public Employee convertToEntity() {

        Employee employee = new Employee();
        employee.setId(this.getId());
        employee.setName(this.getName());
        employee.setPhoneNumber(this.getPhoneNumber());
        employee.setRole(this.getRole());
        employee.setPasswordHash(this.getPasswordHash());
        employee.setEnabled(this.getEnabled());

        return employee;
    }
}
