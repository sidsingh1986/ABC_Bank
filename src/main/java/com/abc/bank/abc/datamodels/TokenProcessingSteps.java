package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.enums.ServiceProcessingType;
import com.abc.bank.abc.enums.TokenServiceStatus;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "Token_processing_steps")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
            throw new NullPointerException("The service to a token processing step can't be set to null");
        }
        this.service = service;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        if (employee == null) {
            throw new NullPointerException("The employee to a token processing step can't be set to null");
        }
        this.employee = employee;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        if (counter == null) {
            throw new NullPointerException("The counter to a token processing step can't be set to null");
        }
        this.counter = counter;
    }

    public String getActionOrComments() {
        return actionOrComments;
    }

    public void setActionOrComments(String actionOrComments) {
        if (actionOrComments == null) {
            throw new NullPointerException("The action or comments to a token processing step can't be set to null");
        }
        this.actionOrComments = actionOrComments;
    }

    public TokenServiceStatus getStatus() {
        return status;
    }

    public void setStatus(TokenServiceStatus status) {
        if (status == null) {
            throw new NullPointerException("The status of a token processing step can't be set to null");
        }
        this.status = status;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public ServiceProcessingType getServiceProcessingType() {
        return serviceProcessingType;
    }

    public void setServiceProcessingType(ServiceProcessingType serviceProcessingType) {
        if (serviceProcessingType == null) {
            throw new NullPointerException("The processing type to a token processing step can't be set to null");
        }
        this.serviceProcessingType = serviceProcessingType;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        if (token == null) {
            throw new NullPointerException("The token to a token processing step can't be set to null");
        }
        this.token = token;
    }
}
