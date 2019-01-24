package com.abc.bank.abc.Models;

import com.abc.bank.abc.DtoModels.ServicesPlaceholder;
import com.abc.bank.abc.DtoModels.TokenDTO;
import com.abc.bank.abc.Enums.TokenStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
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

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "Token_id")
    private List<TokenService> tokenServices;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Token_id")
    private List<TokenMultiCounterService> tokenMultiCounterServices;

    @Transient
    private TokenService currentTokenService;

    public TokenDTO convertToDTO() {
        TokenDTO token = new TokenDTO();
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
