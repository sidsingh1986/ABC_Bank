package com.abc.bank.abc.Models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
