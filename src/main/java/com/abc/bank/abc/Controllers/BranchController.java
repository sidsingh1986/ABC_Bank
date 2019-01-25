package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.ViewModels.BankingServiceModel;
import com.abc.bank.abc.ViewModels.BranchModel;
import com.abc.bank.abc.ViewModels.CounterModel;
import com.abc.bank.abc.ViewModels.MultiCounterBankingServiceModel;
import com.abc.bank.abc.DataModels.BankingService;
import com.abc.bank.abc.DataModels.Branch;
import com.abc.bank.abc.DataModels.Counter;
import com.abc.bank.abc.DataModels.MultiCounterBankingService;
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
    public List<BranchModel> getBranches() {
        List<Branch> branches = branchService.getAllBranches();
        List<BranchModel> branchModels = new ArrayList<>();

        for (int index = 0; index < branches.size(); index++) {
            branchModels.add(branches.get(index).convertToDTO());
        }

        return branchModels;
    }

    /**
     * For getting a specific branch
     *
     * @param branchId
     * @return branch if existing null otherwise
     * @throws com.abc.bank.abc.Exceptions.ResourceNotFoundException if the branch with branchId is not found
     */
    @ApiOperation(value = "View a particular branches of bank")
    @GetMapping("/branches/{id}")
    public BranchModel getBranch(@PathVariable(value = "id") Integer branchId) {
        return branchService.getBranch(branchId).convertToDTO();
    }

    /**
     * For creating a new branch
     *
     * @param branchModel
     * @return new branch instance
     */
    @ApiOperation(value = "Creates a new branch of the bank")
    @PostMapping("/branches")
    public BranchModel createBranch(@Valid @RequestBody BranchModel branchModel) {
        Branch branch = branchModel.convertToEntity();
        return branchService.createNewBranch(branch).convertToDTO();
    }

    /**
     * For updating a existing branch
     *
     * @param branchModel
     */
    @ApiOperation(value = "Updates a branch of the bank")
    @PutMapping("/branches")
    public void updateBranch(@Valid @RequestBody BranchModel branchModel) {
        Branch branch = branchModel.convertToEntity();
        branchService.updateBranch(branch);
    }

    /**
     * For deleting a existing branch
     *
     * @param branchId
     */
    @ApiOperation(value = "Deletes a branch of the bank")
    @DeleteMapping("/branches/{branchId}")
    public void deleteBranch(@PathVariable(value = "branchId") Integer branchId) {
        branchService.deleteBranch(branchId);
    }

    /**
     * For getting the services offered by a branch
     *
     * @param branchId
     * @returns list of banking services
     */
    @ApiOperation(value = "View the services handled by the branch")
    @GetMapping("/branches/{id}/services")
    public List<BankingServiceModel> getBranchServices(@PathVariable(name = "id") Integer branchId) {
        List<BankingService> bankingServices = branchService.getBankingServices(branchId);

        List<BankingServiceModel> bankingServiceRespons = new ArrayList<>();
        if (bankingServices != null) {
            for (int index = 0; index < bankingServices.size(); index++) {
                bankingServiceRespons.add(bankingServices.get(index).convertToDTO());
            }
        } else {
            throw new NullPointerException("The list of services for a branch can't be null");
        }
        return bankingServiceRespons;
    }

    /**
     * For getting a particular service offered by a branch
     *
     * @param branchId
     * @param serviceId
     * @returns banking service
     */
    @ApiOperation(value = "View the particular services handled by the branch")
    @GetMapping("/branches/{id}/service/{serviceId}")
    public BankingServiceModel getBranchService(@PathVariable(value = "id") Integer branchId,
                                                @PathVariable(value = "serviceId") Integer serviceId) {
        return branchService.getBankingService(branchId, serviceId).convertToDTO();
    }

    /**
     * Adds a service to the list of existing services served by a branch
     *
     * @param branchId
     * @param bankingServiceModel
     * @returns branch with updated services
     */
    @ApiOperation(value = "Add a new service to the services a particular branch servers")
    @PutMapping("/branches/{id}/service")
    public Branch addBranchService(@PathVariable(value = "id") Integer branchId,
                                           @Valid @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        return branchService.addBankingService(branchId, bankingService);
    }

    /**
     * Gets list of multi counter services offered by a branch
     *
     * @param branchId
     * @returns list of multi counter services
     */
    @ApiOperation(value = "View the multi-counter services handled by branch")
    @GetMapping("/branches/{id}/multipleCounterServices")
    public List<MultiCounterBankingServiceModel> getBranchMultiCounterServices(@PathVariable(value = "id") Integer branchId) {
        List<MultiCounterBankingService> multiCounterBankingServices = branchService.getMultiCounterBankingServices(branchId);

        List<MultiCounterBankingServiceModel> multiCounterBankingServiceModels = new ArrayList<>();

        for(int index = 0; index < multiCounterBankingServices.size(); index++) {
            multiCounterBankingServiceModels.add(multiCounterBankingServices.get(index).convertToDTO());
        }
        return multiCounterBankingServiceModels;
    }

    /**
     * Gets a particular multi counter service offered by a branch
     *
     * @param branchId
     * @param serviceId
     * @returns multi counter service
     * @throws com.abc.bank.abc.Exceptions.ResourceNotFoundException when the service is not served by  branch
     */
    @ApiOperation(value = "View a particular multi-counter service handled by branch")
    @GetMapping("/branches/{id}/multipleCounterServices/{serviceId}")
    public MultiCounterBankingServiceModel getBranchMultiCounterService(@PathVariable(value = "id") Integer branchId,
                                                                        @PathVariable(value = "serviceId") Integer serviceId) {
        return branchService.getMultiCounterBankingService(branchId, serviceId).convertToDTO();
    }

    /**
     * Adds a multi counter service to the multi counter services offered by a branch
     *
     * @param branchId
     * @param multiCounterBankingServiceModel
     * @returns branch with added multi counter service
     * @throws com.abc.bank.abc.Exceptions.ResourceNotFoundException when branch does not exist
     */
    @ApiOperation(value = "Add a new multi-counter service to the services a particular branch servers")
    @PutMapping("/branches/{id}/multipleCounterServices")
    public Branch addBranchMultiCounterService(@PathVariable(value = "id") Integer branchId,
                                               @Valid @RequestBody MultiCounterBankingServiceModel multiCounterBankingServiceModel) {
        MultiCounterBankingService multiCounterBankingService = multiCounterBankingServiceModel.convertToEntity();
        return branchService.addMultiCounterBankingService(branchId, multiCounterBankingService);
    }

    /**
     * Gets the list of counters present in the branch
     *
     * @param branchId
     * @returns list of counters present in the branch
     * @throws com.abc.bank.abc.Exceptions.ResourceNotFoundException when branch does not exist or
     * no counter is present in branch
     */
    @ApiOperation(value = "View the counters present in a branch")
    @GetMapping("/branches/{id}/counters")
    public List<CounterModel> getBranchCounters(@PathVariable(value = "id") Integer branchId) {
        List<Counter> counters = branchService.getCounters(branchId);
        List<CounterModel> counterModels = new ArrayList<>();

        if (counters != null) {
            for (int index = 0; index < counters.size(); index++) {
                counterModels.add(counters.get(index).convertToDTO());
            }
        } else {
            throw new NullPointerException("The list of counters of branch can't be Null");
        }
        return counterModels;
    }

    /**
     * Gets a particular counter present in the branch
     *
     * @param branchId
     * @param counterId
     * @returns counter with the id passed
     * @throws com.abc.bank.abc.Exceptions.ResourceNotFoundException when branch does not exist or
     * counter with passed id is not present in branch
     */
    @ApiOperation(value = "View a particular counter present in a branch")
    @GetMapping("/branches/{id}/counters/{counterId}")
    public CounterModel getBranchCounter(@PathVariable(value = "id") Integer branchId,
                                         @PathVariable(value = "counterId") Integer counterId) {
        return branchService.getCounter(branchId, counterId).convertToDTO();
    }

    /**
     * Gets a particular counter present in the branch
     *
     * @param branchId
     * @param counterModel
     * @returns branch with counter added
     * @throws com.abc.bank.abc.Exceptions.ResourceNotFoundException when branch does not exist or
     * counter with passed id is not present in branch
     */
    @ApiOperation(value = "Add a counter to a particular branch")
    @PutMapping("/branches/{id}/counters")
    public Branch addBranchCounter(@PathVariable(value = "id") Integer branchId,
                                   @Valid @RequestBody CounterModel counterModel) {
        Counter counter = counterModel.convertToEntity();
        return branchService.addCounter(branchId, counter);
    }
}
