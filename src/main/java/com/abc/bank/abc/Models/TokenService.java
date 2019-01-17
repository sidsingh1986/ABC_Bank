package com.abc.bank.abc.Models;

import com.abc.bank.abc.Enums.TokenServiceStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class TokenService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private BankingService bankingService;

    @Column(name = "action_or_comments")
    private String actionOrComments;

    @OneToOne
    private Counter counter;

    @Column(name = "request_order")
    private int  requestOrder;

    @Enumerated(EnumType.STRING)
    private TokenServiceStatus status;

    @OneToOne
    private Employee servedBy;
}
