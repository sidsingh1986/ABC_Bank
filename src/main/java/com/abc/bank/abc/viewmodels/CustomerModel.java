package com.abc.bank.abc.viewmodels;

import com.abc.bank.abc.enums.CustomerType;
import com.abc.bank.abc.datamodels.Customer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerModel {
    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    private String phoneNumber;

    @NotNull
    private CustomerType customerType;

    @NotNull
    private AddressModel address;

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

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        if (address == null) {
            throw new NullPointerException("The address of a customer can't be null");
        }
        this.address = address;
    }

    public Customer convertToEntity() {
        Customer customer = new Customer();
        customer.setId(this.getId());
        customer.setName(this.getName());
        customer.setPhoneNumber(this.getPhoneNumber());
        customer.setCustomerType(this.getCustomerType());
        customer.setAddress(this.getAddress().convertToEntity());

        return customer;
    }
}
