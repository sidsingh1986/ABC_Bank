package com.abc.bank.abc.viewmodels;

import com.abc.bank.abc.datamodels.Address;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    public void setFirstLine(String firstLine) {
        if (firstLine == null) {
            throw new NullPointerException("firstline of address can't be null");
        }
        this.firstLine = firstLine;
    }

    public void setSecondLine(String secondLine) {
        if (secondLine == null) {
            throw new NullPointerException("secondline of address can't be null");
        }
        this.secondLine = secondLine;
    }

    public void setThirdLine(String thirdLine) {
        if (thirdLine == null) {
            throw new NullPointerException("thirdline of address can't be null");
        }
        this.thirdLine = thirdLine;
    }

    public void setPincode(String pincode) {
        if (pincode == null) {
            throw new NullPointerException("pincode of address can't be null");
        }
        this.pincode = pincode;
    }

    public void setCity(String city) {
        if (city == null) {
            throw new NullPointerException("city of address can't be null");
        }
        this.city = city;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public String getSecondLine() {
        return secondLine;
    }

    public String getThirdLine() {
        return thirdLine;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCity() {
        return city;
    }

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
