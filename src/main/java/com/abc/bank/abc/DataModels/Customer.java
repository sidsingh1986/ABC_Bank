package com.abc.bank.abc.DataModels;

import com.abc.bank.abc.ViewModels.CustomerModel;
import com.abc.bank.abc.Enums.CustomerType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
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
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Branch_id")
    private Branch branch;

    public CustomerModel convertToDTO() {
        CustomerModel customer = new CustomerModel();
        customer.setId(this.getId());
        customer.setName(this.getName());
        customer.setPhoneNumber(this.getPhoneNumber());
        customer.setCustomerType(this.getCustomerType());
        customer.setBranch(this.getBranch().convertToDTO());
        customer.setAddress(this.getAddress().convertToDTO());

        return customer;
    }
}
