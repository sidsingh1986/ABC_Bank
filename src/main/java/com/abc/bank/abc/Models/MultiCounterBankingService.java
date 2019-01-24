package com.abc.bank.abc.Models;

import com.abc.bank.abc.DtoModels.BankingServiceDTO;
import com.abc.bank.abc.DtoModels.MultiCounterBankingServiceDTO;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Multi_counter_service_has_Services", joinColumns = { @JoinColumn(name = "Multi_counter_service_id") }, inverseJoinColumns = { @JoinColumn(name = "Services_id") })
    List<BankingService> bankingServices;

    public MultiCounterBankingServiceDTO convertToDTO() {
        MultiCounterBankingServiceDTO multiCounterBankingService = new MultiCounterBankingServiceDTO();

        multiCounterBankingService.setId(this.getId());
        multiCounterBankingService.setName(this.getName());
        List<BankingService> bankingService = this.getBankingServices();
        List<BankingServiceDTO> bankingServiceDTOS = new ArrayList<>();
        for (int index = 0; index < bankingService.size(); index++) {
            bankingServiceDTOS.add(bankingService.get(index).convertToDTO());
        }

        multiCounterBankingService.setBankingServices(bankingServiceDTOS);
        multiCounterBankingService.setServiceProcessingType(this.getServiceProcessingType());
        return multiCounterBankingService;
    }
}
