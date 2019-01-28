package com.abc.bank.abc.DataModels;

import com.abc.bank.abc.ViewModels.ServicesPlaceholder;
import com.abc.bank.abc.ViewModels.TokenModel;
import com.abc.bank.abc.Enums.TokenStatus;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Data
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

    @Transient
    private List<ServicesPlaceholder> bankingServicesPlaceholder;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "Token_id")
    private List<TokenService> tokenServices;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Token_id")
    private List<TokenMultiCounterService> tokenMultiCounterServices;

    @Transient
    private TokenService currentTokenService;

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
