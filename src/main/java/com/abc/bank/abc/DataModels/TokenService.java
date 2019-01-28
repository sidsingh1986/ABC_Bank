package com.abc.bank.abc.DataModels;

import com.abc.bank.abc.Enums.TokenServiceStatus;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Token_Services")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TokenService implements Comparable<TokenService> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Services_id")
    private BankingService service;

    @ManyToOne
    @JoinColumn(name = "Token_id")
    private Token token;

    @Column(name = "processing_order")
    private int  processingOrder;

    @Enumerated(EnumType.STRING)
    private TokenServiceStatus status;

    @Override
    public int compareTo(TokenService service) {
        if (service != null) {
            return Integer.compare(this.getProcessingOrder(), service.getProcessingOrder());
        } else {
            throw new NullPointerException("The service passed to compare can't be null");
        }
    }
}
