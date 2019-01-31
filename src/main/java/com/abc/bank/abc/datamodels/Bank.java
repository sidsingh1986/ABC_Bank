package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.viewmodels.BankModel;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Branch> branches;

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
            throw new NullPointerException("The name parameter for bank can't be null");
        }
        this.name = name;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        if (branches == null) {
            throw new NullPointerException("The branches list to bank can't be null");
        }
        this.branches = branches;
    }

    public BankModel convertToDTO() {
        BankModel bank = new BankModel();
        bank.setId(this.getId());
        bank.setName(this.getName());

        return bank;
    }
}
