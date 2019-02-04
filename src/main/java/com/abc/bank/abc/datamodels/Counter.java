package com.abc.bank.abc.datamodels;

import com.abc.bank.abc.exceptions.IllegalInputException;
import com.abc.bank.abc.viewmodels.BankingServiceModel;
import com.abc.bank.abc.viewmodels.CounterModel;
import com.abc.bank.abc.viewmodels.TokenModel;
import com.abc.bank.abc.enums.CustomerType;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_service")
    private CustomerType customerType;

    @Column(name = "display_number")
    private Integer displayNumber;

    public Integer getDisplayNumber() {
        return displayNumber;
    }

    public void setDisplayNumber(Integer displayNumber) {
        this.displayNumber = displayNumber;
    }

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name="Counter_Services", joinColumns=@JoinColumn(name="Counter_id"), inverseJoinColumns=@JoinColumn(name="Services_id"))
    private List<BankingService> servicesOffered;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "counter", cascade = CascadeType.ALL)
    private List<Token> tokens;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Branch_id")
    private Branch branch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalInputException("the name of the counter can't be null");
        }
        this.name = name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        if (customerType == null) {
            throw new IllegalInputException("the customer type of the counter can't be null");
        }
        this.customerType = customerType;
    }

    public List<BankingService> getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(List<BankingService> servicesOffered) {
        if (servicesOffered == null) {
            throw new IllegalInputException("the services offered by the counter can't be null");
        }
        this.servicesOffered = servicesOffered;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        if (tokens == null) {
            throw new IllegalInputException("the tokens of the counter can't be null");
        }
        this.tokens = tokens;
    }

    public CounterModel convertToDTO() {

        CounterModel counter = new CounterModel();

        counter.setId(this.getId());
        counter.setName(this.getName());
        counter.setCustomerType(this.getCustomerType());

        List<BankingService> bankingServices = this.getServicesOffered();

        List<BankingServiceModel> bankingServicesDTOS = new ArrayList<>();
        for (int index = 0; index < bankingServices.size(); index++) {
            bankingServicesDTOS.add(bankingServices.get(index).convertToDTO());
        }
        counter.setServicesOffered(bankingServicesDTOS);

        List<Token> tokens = this.getTokens();

        List<TokenModel> tokensDTOS = new ArrayList<>();

        for (int index = 0;index < tokens.size(); index++) {
            tokensDTOS.add(tokens.get(index).convertToDTO());
        }

        counter.setTokens(tokensDTOS);

        return counter;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        if (branch == null) {
            throw new IllegalInputException("the branch of counter can't be null");
        }
        this.branch = branch;
    }

    public boolean addToken(Token token) {
        if (token != null) {
            if (tokens == null) {
                tokens = new ArrayList<>();
            }
            return tokens.add(token);
        } else {
            throw new IllegalInputException("The token added to counter is null");
        }
    }

    public boolean removeToken(Token token) {
        if (tokens == null) {
            throw new NullPointerException("cannot remove token from the null token list");
        }
        return tokens.remove(token);
    }

    public boolean addService(BankingService bankingService) {
        if (servicesOffered == null)
            servicesOffered = new ArrayList<>();

        if(bankingService == null) {
            throw new IllegalInputException("The banking service to add to counter is null");
        }
        return servicesOffered.add(bankingService);
    }

    public boolean removeService(BankingService bankingService) {

        if (servicesOffered == null)
            throw new IllegalInputException("The list of services for counter can't be null");

        if(bankingService == null) {
            throw new IllegalInputException("The banking service to remove from counter is null");
        }
        return servicesOffered.remove(bankingService);
    }
}
