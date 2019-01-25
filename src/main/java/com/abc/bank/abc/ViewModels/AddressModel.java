package com.abc.bank.abc.ViewModels;

import com.abc.bank.abc.DataModels.Address;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddressModel {

    private int id;

    @NotNull
    @Size(min=1, max = 45)
    private String firstLine;

    @Size(max = 45)
    private String secondLine;

    @Size(max = 45)
    private String thirdLine;

    @NotNull
    private String pincode;

    @NotNull
    private String city;

    public Address convertToEntity() {
        Address address = new Address();
        address.setId(this.getId());
        address.setFirstLine(this.getFirstLine());
        address.setSecondLine(this.getSecondLine());
        address.setThirdLine(this.getThirdLine());
        address.setPincode(this.getPincode());
        address.setCity(this.getCity());

        return address;
    }
}
