package com.abc.bank.abc.ViewModels;

import com.abc.bank.abc.Enums.CustomerType;
import com.abc.bank.abc.DataModels.Customer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
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

    @NotNull
    private BranchModel branch;

    public Customer convertToEntity() {
        Customer customer = new Customer();
        customer.setId(this.getId());
        customer.setName(this.getName());
        customer.setPhoneNumber(this.getPhoneNumber());
        customer.setCustomerType(this.getCustomerType());
        customer.setBranch(this.getBranch().convertToEntity());
        customer.setAddress(this.getAddress().convertToEntity());
        customer.setBranch(this.getBranch().convertToEntity());

        return customer;
    }
}
