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

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "Token_id")
    private List<TokenService> tokenServices;

    @Transient
    private TokenService currentTokenService;
}
