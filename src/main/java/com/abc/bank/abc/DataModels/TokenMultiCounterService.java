package com.abc.bank.abc.DataModels;

import com.abc.bank.abc.Enums.TokenServiceStatus;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Token_Multi_counter_service")
public class TokenMultiCounterService implements Comparable<TokenMultiCounterService> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Multi_counter_service_id")
    private MultiCounterBankingService service;

    @ManyToOne
    @JoinColumn(name = "Token_id")
    private Token token;

    @Column(name = "processing_order")
    private int  processingOrder;

    @Enumerated(EnumType.STRING)
    private TokenServiceStatus status;

    @Override
    public int compareTo(TokenMultiCounterService service) {
        if (service != null) {
            return Integer.compare(this.getProcessingOrder(), service.getProcessingOrder());
        } else {
            throw new NullPointerException("The service passed to compare can't be null");
        }
    }
}
