package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.enums.TokenServiceStatus;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "Token_Multi_counter_service")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TokenMultiCounterService  {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MultiCounterBankingService getService() {
        return service;
    }

    public void setService(MultiCounterBankingService service) {
        if (service == null) {
            throw new NullPointerException("The service of an token multi counter service can't be set to null");
        }
        this.service = service;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        if (token == null) {
            throw new NullPointerException("The token of an token multi counter service can't be set to null");
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
            throw new NullPointerException("The status of an token multi counter service can't be set to null");
        }
        this.status = status;
    }
}
