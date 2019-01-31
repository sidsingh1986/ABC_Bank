package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.enums.TokenServiceStatus;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "Token_Services")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TokenService {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BankingService getService() {
        return service;
    }

    public void setService(BankingService service) {
        if (service == null) {
            throw new NullPointerException("The service to a token service can't be set to null");
        }
        this.service = service;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        if (token == null) {
            throw new NullPointerException("The token to a token service can't be set to null");
        }
        this.token = token;
    }

    public int getProcessingOrder() {
        return processingOrder;
    }

    public void setProcessingOrder(int processingOrder) {
        this.processingOrder = processingOrder;
    }

    public TokenServiceStatus getStatus() {
        return status;
    }

    public void setStatus(TokenServiceStatus status) {
        if (status == null) {
            throw new NullPointerException("The status of a token service can't be set to null");
        }
        this.status = status;
    }
}
