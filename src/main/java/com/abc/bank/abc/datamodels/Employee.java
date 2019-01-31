package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.viewmodels.EmployeeModel;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Employee_Roles", joinColumns = { @JoinColumn(name = "Employee_id") }, inverseJoinColumns = { @JoinColumn(name = "Roles_id") })
    private List<Roles> roles;

    @Column(name = "enabled")
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
