package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.viewmodels.CustomerModel;
import com.abc.bank.abc.enums.CustomerType;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_service")
    private CustomerType customerType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "Branch_id")
    private Branch branch;

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
            throw new NullPointerException("The name of customer can't be null");
        }
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            throw new NullPointerException("The phone number of customer can't be null");
        }
        this.phoneNumber = phoneNumber;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        if (customerType == null) {
            throw new NullPointerException("The customer type of a customer can't be null");
        }
        this.customerType = customerType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (address == null) {
            throw new NullPointerException("The address of a customer can't be null");
        }
        this.address = address;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        if (branch == null) {
            throw new NullPointerException("The branch of a customer can't be set to null");
        }
        this.branch = branch;
    }

    public CustomerModel convertToDTO() {
        CustomerModel customer = new CustomerModel();
        customer.setId(this.getId());
        customer.setName(this.getName());
        customer.setPhoneNumber(this.getPhoneNumber());
        customer.setCustomerType(this.getCustomerType());
        customer.setAddress(this.getAddress().convertToDTO());

        return customer;
    }
}
