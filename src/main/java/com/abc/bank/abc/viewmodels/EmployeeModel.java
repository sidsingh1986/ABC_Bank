package com.abc.bank.abc.viewmodels;

import com.abc.bank.abc.datamodels.Employee;
import com.abc.bank.abc.datamodels.Roles;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("The name of an employee can't be set to null");
        }
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash == null) {
            throw new NullPointerException("The passwordHash of an employee can't be set to null");
        }
        this.passwordHash = passwordHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (passwordHash == null) {
            throw new NullPointerException("The passwordHash of an employee can't be set to null");
        }
        this.phoneNumber = phoneNumber;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        if (roles == null) {
            throw new NullPointerException("The roles of an employee can't be set to null");
        }
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

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
