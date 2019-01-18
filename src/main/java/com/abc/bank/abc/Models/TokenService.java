package com.abc.bank.abc.Models;

import com.abc.bank.abc.Enums.TokenServiceStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Token_Services")
public class TokenService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Services_id")
    private BankingService service;

    @Column(name = "actions_or_comments")
    private String actionOrComments;

    @OneToOne
    private Counter counter;

    @Column(name = "request_order")
    private int  requestOrder;

    @Enumerated(EnumType.STRING)
    private TokenServiceStatus status;

    @OneToOne
    @JoinColumn(name = "served_by_id")
    private Employee servedBy;
}
