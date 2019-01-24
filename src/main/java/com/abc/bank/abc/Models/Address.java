package com.abc.bank.abc.Models;

import com.abc.bank.abc.DtoModels.AddressDTO;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_line")
    private String firstLine;

    @Column(name = "second_line")
    private String secondLine;

    @Column(name = "third_line")
    private String thirdLine;

    private String pincode;

    private String city;


    public AddressDTO convertToDTO() {
        AddressDTO address = new AddressDTO();
        address.setId(this.getId());
        address.setFirstLine(this.getFirstLine());
        address.setSecondLine(this.getSecondLine());
        address.setThirdLine(this.getThirdLine());
        address.setPincode(this.getPincode());
        address.setCity(this.getCity());

        return address;
    }

}
