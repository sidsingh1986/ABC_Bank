package com.abc.bank.abc.DataModels;

import com.abc.bank.abc.ViewModels.BankingServiceModel;
import com.abc.bank.abc.ViewModels.MultiCounterBankingServiceModel;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Multi_counter_service")
public class MultiCounterBankingService extends BaseBankingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Multi_counter_service_has_Services", joinColumns = { @JoinColumn(name = "Multi_counter_service_id") }, inverseJoinColumns = { @JoinColumn(name = "Services_id") })
    List<BankingService> bankingServices;

    public MultiCounterBankingServiceModel convertToDTO() {
        MultiCounterBankingServiceModel multiCounterBankingService = new MultiCounterBankingServiceModel();

        multiCounterBankingService.setId(this.getId());
        multiCounterBankingService.setName(this.getName());
        List<BankingService> bankingService = this.getBankingServices();
        List<BankingServiceModel> bankingServiceRespons = new ArrayList<>();
        for (int index = 0; index < bankingService.size(); index++) {
            bankingServiceRespons.add(bankingService.get(index).convertToDTO());
        }

        multiCounterBankingService.setBankingServices(bankingServiceRespons);
        multiCounterBankingService.setServiceProcessingType(this.getServiceProcessingType());
        return multiCounterBankingService;
    }
}
