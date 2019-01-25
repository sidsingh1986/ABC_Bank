package com.abc.bank.abc.Models;

import com.abc.bank.abc.DtoModels.EmployeeDTO;
import com.abc.bank.abc.Enums.EmployeeRoles;
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

    public EmployeeDTO convertToDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(this.getId());
        employeeDTO.setName(this.getName());
        employeeDTO.setPhoneNumber(this.getPhoneNumber());
        employeeDTO.setRoles(this.getRoles());
        employeeDTO.setPasswordHash(this.getPasswordHash());
        employeeDTO.setEnabled(this.isEnabled());

        return employeeDTO;
    }
}
