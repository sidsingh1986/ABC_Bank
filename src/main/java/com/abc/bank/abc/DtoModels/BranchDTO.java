package com.abc.bank.abc.DtoModels;

import com.abc.bank.abc.Enums.ServiceProcessingType;
import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.Branch;
import com.abc.bank.abc.Models.Counter;
import com.abc.bank.abc.Models.MultiCounterBankingService;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class BranchDTO {

    private int id;

    @NotNull(message = "name can't be null")
    @Size(min = 1, max = 45, message = "size of name has to between 1 and 45 characters")
    private String name;

    @NotNull
    private AddressDTO address;

    @NotNull
    private BankDTO bank;

    @NotNull
    private List<BankingServiceInterface> bankingServices;

    List<CounterDTO> counters;

    public Branch convertToEntity() {
        Branch branch = new Branch();
        branch.setId(this.getId());
        branch.setName(this.getName());
        branch.setAddress(this.getAddress().convertToEntity());
        branch.setBank(this.getBank().convertToEntity());

        List<CounterDTO> counterDTOS = this.getCounters();
        List<Counter> counters = new ArrayList<>();
        for (int index = 0; index < counterDTOS.size(); index++) {
            counters.add(counterDTOS.get(index).convertToEntity());
        }
        branch.setCounters(counters);

        List<BankingServiceInterface> bankingServiceList = this.getBankingServices();
        List<MultiCounterBankingService> multiCounterBankingServices = new ArrayList<>();
        List<BankingService> bankingServices = new ArrayList<>();
        for (int index = 0; index < bankingServiceList.size(); index++) {
            if (bankingServiceList.get(index).getServiceProcessingType().equals(ServiceProcessingType.MULTI_COUNTER)) {
                MultiCounterBankingServiceDTO multiCounterBankingServiceDTO = (MultiCounterBankingServiceDTO)bankingServiceList.get(index);
                multiCounterBankingServices.add(multiCounterBankingServiceDTO.convertToEntity());
            } else {
                BankingServiceDTO bankingServiceDTO = (BankingServiceDTO) bankingServiceList.get(index);
                bankingServices.add(bankingServiceDTO.convertToEntity());
            }
        }
        branch.setMultiCounterBankingServices(multiCounterBankingServices);
        branch.setBankingServices(bankingServices);

        return branch;
    }
}
