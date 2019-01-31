package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.viewmodels.ServicesPlaceholder;
import com.abc.bank.abc.viewmodels.TokenModel;
import com.abc.bank.abc.enums.TokenStatus;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Token implements Comparable<Token>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    @ManyToOne
    @JoinColumn(name = "Counter_id")
    private Counter counter;

    @Transient
    private List<ServicesPlaceholder> bankingServicesPlaceholder;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "token", cascade = CascadeType.ALL)
    private List<TokenService> tokenServices;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "token", cascade = CascadeType.ALL)
    private List<TokenMultiCounterService> tokenMultiCounterServices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new NullPointerException("The customer of a token can't be set to null");
        }
        this.customer = customer;
    }

    public TokenStatus getStatus() {
        return status;
    }

    public void setStatus(TokenStatus status) {
        if (status == null) {
            throw new NullPointerException("The status of a token can't be set to null");
        }
        this.status = status;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        if (counter == null) {
            throw new NullPointerException("The counter of a token can't be set to null");
        }
        this.counter = counter;
    }

    public List<ServicesPlaceholder> getBankingServicesPlaceholder() {
        return bankingServicesPlaceholder;
    }

    public void setBankingServicesPlaceholder(List<ServicesPlaceholder> bankingServicesPlaceholder) {
        if (bankingServicesPlaceholder == null) {
            throw new NullPointerException("The banking services of a token can't be set to null");
        }
        this.bankingServicesPlaceholder = bankingServicesPlaceholder;
    }

    public List<TokenService> getTokenServices() {
        return tokenServices;
    }

    public void setTokenServices(List<TokenService> tokenServices) {
        if (tokenServices == null) {
            throw new NullPointerException("The tokenServices of a token can't be set to null");
        }
        this.tokenServices = tokenServices;
    }

    public List<TokenMultiCounterService> getTokenMultiCounterServices() {
        return tokenMultiCounterServices;
    }

    public void setTokenMultiCounterServices(List<TokenMultiCounterService> tokenMultiCounterServices) {
        if (tokenMultiCounterServices == null) {
            throw new NullPointerException("The tokenMultiCounterServices of a token can't be set to null");
        }
        this.tokenMultiCounterServices = tokenMultiCounterServices;
    }

    public TokenModel convertToDTO() {
        TokenModel token = new TokenModel();
        token.setId(this.getId());
        token.setStatus(this.getStatus());
        token.setCustomer(this.getCustomer().convertToDTO());
        token.setBankingServices(this.getBankingServicesPlaceholder());

        return token;
    }

    @Override
    public int compareTo(Token token) {
        if (token != null) {
            return Integer.compare(this.getId(), token.getId());
        } else {
            throw new NullPointerException("The token passed to compare can't be null");
        }
    }
}
