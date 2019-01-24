package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.DtoModels.BankingServiceDTO;
import com.abc.bank.abc.DtoModels.BranchDTO;
import com.abc.bank.abc.DtoModels.CounterDTO;
import com.abc.bank.abc.DtoModels.MultiCounterBankingServiceDTO;
import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.Branch;
import com.abc.bank.abc.Models.Counter;
import com.abc.bank.abc.Models.MultiCounterBankingService;
import com.abc.bank.abc.Services.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value = "Branch", description = "Operations pertaining to the branch of a bank")
@RestController
public class BranchController {

    @Autowired
    BranchService branchService;

    /**
     * For getting all the branches registered in the system
     *
     * @return list of all branches
     */
    @ApiOperation(value = "View all branches of bank")
    @GetMapping("/branches")
    public List<BranchDTO> getBranches() {
        List<Branch> branches = branchService.getAllBranches();
        List<BranchDTO> branchDTOS = new ArrayList<>();

        for (int index = 0; index < branches.size(); index++) {
            branchDTOS.add(branches.get(index).convertToDTO());
        }

        return branchDTOS;
    }

    /**
     * For getting a specific branch
     *
     * @param branchId
     * @return branch if existing null otherwise
     */
    @ApiOperation(value = "View a particular branches of bank")
    @GetMapping("/branches/{id}")
    public BranchDTO getBranch(@PathVariable(value = "id") Integer branchId) {
        return branchService.getBranch(branchId).convertToDTO();
    }

    /**
     * For creating a new branch
     *
     * @param branchDTO
     * @return new branch instance
     */
    @ApiOperation(value = "Creates a new branch of the bank")
    @PostMapping("/branches")
    public BranchDTO createBranch(@Valid @RequestBody BranchDTO branchDTO) {
        Branch branch = branchDTO.convertToEntity();
        return branchService.createNewBranch(branch).convertToDTO();
    }

    @ApiOperation(value = "Updates a branch of the bank")
    @PutMapping("/branches")
    public void updateBranch(@Valid @RequestBody BranchDTO branchDTO) {
        Branch branch = branchDTO.convertToEntity();
        branchService.updateBranch(branch);
    }

    @ApiOperation(value = "Updates a branch of the bank")
    @DeleteMapping("/branches/{branchId}")
    public void deleteBranch(@PathVariable(value = "branchId") Integer branchId) {
        branchService.deleteBranch(branchId);
    }

    @ApiOperation(value = "View the services handled by the branch")
    @GetMapping("/branches/{id}/services")
    public List<BankingServiceDTO> getBranchServices(@PathVariable(name = "id") Integer branchId) {
        List<BankingService> bankingServices = branchService.getBankingServices(branchId);

        List<BankingServiceDTO> bankingServiceDTOS = new ArrayList<>();
        if (bankingServices != null) {
            for (int index = 0; index < bankingServices.size(); index++) {
                bankingServiceDTOS.add(bankingServices.get(index).convertToDTO());
            }
        } else {
            throw new NullPointerException("The list of services for a branch can't be null");
        }
        return bankingServiceDTOS;
    }

    @ApiOperation(value = "View the particular services handled by the branch")
    @GetMapping("/branches/{id}/service/{serviceId}")
    public BankingServiceDTO getBranchService(@PathVariable(value = "id") Integer branchId,
                                           @PathVariable(value = "serviceId") Integer serviceId) {
        return branchService.getBankingService(branchId, serviceId).convertToDTO();
    }

    @ApiOperation(value = "Add a new service to the services a particular branch servers")
    @PutMapping("/branches/{id}/service")
    public Branch addBranchService(@PathVariable(value = "id") Integer branchId,
                                           @Valid @RequestBody BankingServiceDTO bankingServiceDTO) {
        BankingService bankingService = bankingServiceDTO.convertToEntity();
        return branchService.addBankingService(branchId, bankingService);
    }

    @ApiOperation(value = "View the multi-counter services handled by branch")
    @GetMapping("/branches/{id}/multipleCounterServices")
    public List<MultiCounterBankingServiceDTO> getBranchMultiCounterServices(@PathVariable(value = "id") Integer branchId) {
        List<MultiCounterBankingService> multiCounterBankingServices = branchService.getMultiCounterBankingServices(branchId);

        List<MultiCounterBankingServiceDTO> multiCounterBankingServiceDTOS = new ArrayList<>();

        for(int index = 0; index < multiCounterBankingServices.size(); index++) {
            multiCounterBankingServiceDTOS.add(multiCounterBankingServices.get(index).convertToDTO());
        }
        return multiCounterBankingServiceDTOS;
    }

    @ApiOperation(value = "View a particular multi-counter service handled by branch")
    @GetMapping("/branches/{id}/multipleCounterServices/{serviceId}")
    public MultiCounterBankingServiceDTO getBranchMultiCounterService(@PathVariable(value = "id") Integer branchId,
                                                                   @PathVariable(value = "serviceId") Integer serviceId) {
        return branchService.getMultiCounterBankingService(branchId, serviceId).convertToDTO();
    }

    @ApiOperation(value = "Add a new multi-counter service to the services a particular branch servers")
    @PutMapping("/branches/{id}/multipleCounterServices")
    public Branch addBranchMultiCounterService(@PathVariable(value = "id") Integer branchId,
                                               @Valid @RequestBody MultiCounterBankingServiceDTO multiCounterBankingServiceDTO) {
        MultiCounterBankingService multiCounterBankingService = multiCounterBankingServiceDTO.convertToEntity();
        return branchService.addMultiCounterBankingService(branchId, multiCounterBankingService);
    }

    @ApiOperation(value = "View the multi-counter services handled by branch")
    @GetMapping("/branches/{id}/counters")
    public List<CounterDTO> getBranchCounters(@PathVariable(value = "id") Integer branchId) {
        List<Counter> counters = branchService.getCounters(branchId);
        List<CounterDTO> counterDTOS = new ArrayList<>();

        if (counters != null) {
            for (int index = 0; index < counters.size(); index++) {
                counterDTOS.add(counters.get(index).convertToDTO());
            }
        } else {
            throw new NullPointerException("The list of counters of branch can't be Null");
        }
        return counterDTOS;
    }

    @ApiOperation(value = "View a particular multi-counter service handled by branch")
    @GetMapping("/branches/{id}/counters/{counterId}")
    public CounterDTO getBranchCounter(@PathVariable(value = "id") Integer branchId,
                                       @PathVariable(value = "counterId") Integer counterId) {
        return branchService.getCounter(branchId, counterId).convertToDTO();
    }

    @ApiOperation(value = "Add a new multi-counter service to the services a particular branch servers")
    @PutMapping("/branches/{id}/counters")
    public Branch addBranchCounter(@PathVariable(value = "id") Integer branchId,
                                   @Valid @RequestBody CounterDTO counterDTO) {
        Counter counter = counterDTO.convertToEntity();
        return branchService.addCounter(branchId, counter);
    }
}
