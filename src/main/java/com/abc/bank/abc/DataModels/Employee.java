package com.abc.bank.abc.DataModels;

import com.abc.bank.abc.ViewModels.EmployeeModel;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String name;

    @Column(name= "password_hash")
    private String passwordHash;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Employee_Roles", joinColumns = { @JoinColumn(name = "Employee_id") }, inverseJoinColumns = { @JoinColumn(name = "Roles_id") })
    private List<Roles> roles;

    @Column(name = "enabled")
    private boolean enabled;

    public EmployeeModel convertToDTO() {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setId(this.getId());
        employeeModel.setName(this.getName());
        employeeModel.setPhoneNumber(this.getPhoneNumber());
        employeeModel.setRoles(this.getRoles());
        employeeModel.setPasswordHash(this.getPasswordHash());
        employeeModel.setEnabled(this.isEnabled());

        return employeeModel;
    }
}