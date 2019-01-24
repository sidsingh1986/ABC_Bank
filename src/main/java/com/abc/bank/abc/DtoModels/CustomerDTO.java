package com.abc.bank.abc.DtoModels;

import com.abc.bank.abc.Enums.CustomerType;
import com.abc.bank.abc.Models.Customer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CustomerDTO {
    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    private String phoneNumber;

    @NotNull
    private CustomerType customerType;

    @NotNull
    private AddressDTO address;

    @NotNull
    private BranchDTO branch;

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
