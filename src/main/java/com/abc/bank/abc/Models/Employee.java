package com.abc.bank.abc.Models;

import com.abc.bank.abc.Enums.EmployeeRoles;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private EmployeeRoles role;

}
