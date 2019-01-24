package com.abc.bank.abc.Models;

import com.abc.bank.abc.DtoModels.EmployeeDTO;
import com.abc.bank.abc.Enums.EmployeeRoles;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private EmployeeRoles role;

    @Column(name = "enabled")
    private short enabled;

    public EmployeeDTO convertToDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(this.getId());
        employeeDTO.setName(this.getName());
        employeeDTO.setPhoneNumber(this.getPhoneNumber());
        employeeDTO.setRole(this.getRole());
        employeeDTO.setPasswordHash(this.getPasswordHash());
        employeeDTO.setEnabled(this.getEnabled());

        return employeeDTO;
    }
}
