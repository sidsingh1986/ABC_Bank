package com.abc.bank.abc.Models;

import com.abc.bank.abc.Enums.ServiceProcessingType;
import com.abc.bank.abc.Enums.TokenServiceStatus;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Token_processing_steps")
public class TokenProcessingSteps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Services_id")
    private BankingService service;

    @ManyToOne
    @JoinColumn(name = "Employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "Counter_id")
    private Counter counter;

    @Column(name = "action_or_comments")
    private String actionOrComments;

    @Enumerated(EnumType.STRING)
    private TokenServiceStatus status;

    @Column(name = "token_service_id")
    private int serviceId;

    @Column(name = "token_service_type")
    @Enumerated(EnumType.STRING)
    private ServiceProcessingType serviceProcessingType;

    @ManyToOne
    @JoinColumn(name = "Token_id")
    private Token token;
}
