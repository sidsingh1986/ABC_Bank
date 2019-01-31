package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.viewmodels.AddressModel;

import javax.persistence.*;

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

    public AddressModel convertToDTO() {
        AddressModel address = new AddressModel();
        address.setId(this.getId());
        address.setFirstLine(this.getFirstLine());
        address.setSecondLine(this.getSecondLine());
        address.setThirdLine(this.getThirdLine());
        address.setPincode(this.getPincode());
        address.setCity(this.getCity());

        return address;
    }

}
