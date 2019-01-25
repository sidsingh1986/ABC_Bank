package com.abc.bank.abc.ViewModels;

import com.abc.bank.abc.DataModels.Employee;
import com.abc.bank.abc.DataModels.Roles;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class EmployeeModel {

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
    private List<Roles> roles;

    @NotNull
    private boolean enabled;

    public Employee convertToEntity() {

        Employee employee = new Employee();
        employee.setId(this.getId());
        employee.setName(this.getName());
        employee.setPhoneNumber(this.getPhoneNumber());
        employee.setRoles(this.getRoles());
        employee.setPasswordHash(this.getPasswordHash());
        employee.setEnabled(this.isEnabled());

        return employee;
    }
}
