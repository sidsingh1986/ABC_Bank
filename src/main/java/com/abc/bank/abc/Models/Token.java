package com.abc.bank.abc.Models;

import com.abc.bank.abc.Enums.TokenStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Customer customer;

    private TokenStatus status;

    @OneToMany
    private List<TokenService> tokenServices;

    @Transient
    private TokenService currentTokenService;
}
