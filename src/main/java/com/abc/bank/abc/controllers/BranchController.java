package com.abc.bank.abc.controllers;

import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.viewmodels.BankingServiceModel;
import com.abc.bank.abc.viewmodels.BranchModel;
import com.abc.bank.abc.viewmodels.CounterModel;
import com.abc.bank.abc.viewmodels.MultiCounterBankingServiceModel;
import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.Branch;
import com.abc.bank.abc.datamodels.Counter;
import com.abc.bank.abc.datamodels.MultiCounterBankingService;
import com.abc.bank.abc.services.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Branch>> getBranches() {

        List<Branch> branches = branchService.getAllBranches();
        if (branches == null) {
            branches = new ArrayList<>();
        }

        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    /**
     * For getting a specific branch
     *
     * @param branchId branch identifier
     * @return branch if existing null otherwise
     * @throws ResourceNotFoundException if the branch with branchId is not found
     */
    @ApiOperation(value = "View a particular branches of bank")
    @GetMapping("/branches/{id}")
    public ResponseEntity<Branch> getBranch(@PathVariable(value = "id") Integer branchId) {
        return new ResponseEntity<>(branchService.getBranch(branchId), HttpStatus.OK);
    }

    /**
     * For creating a new branch
     *
     * @param branchModel branch model instance
     * @return new branch instance
     */
    @ApiOperation(value = "Creates a new branch of the bank")
    @PostMapping("/branches")
    public ResponseEntity<Branch> createBranch(@Valid @RequestBody BranchModel branchModel) {
        Branch branch = branchModel.convertToEntity();
        return new ResponseEntity<>(branchService.createNewBranch(branch), HttpStatus.CREATED);
    }

    /**
     * For updating a existing branch
     *
     * @param branchModel branch model instance
     * @param branchId branch identifier
     */
    @ApiOperation(value = "Updates a branch of the bank")
    @PutMapping("/branches/{id}")
    public ResponseEntity<HttpStatus> updateBranch(@Valid @RequestBody BranchModel branchModel, @PathVariable("id") Integer branchId) {
        Branch branch = branchModel.convertToEntity();
        branchService.updateBranch(branch, branchId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * For deleting a existing branch
     *
     * @param branchId branch identifier
     */
    @ApiOperation(value = "Deletes a branch of the bank")
    @DeleteMapping("/branches/{branchId}")
    public ResponseEntity<HttpStatus> deleteBranch(@PathVariable(value = "branchId") Integer branchId) {
        branchService.deleteBranch(branchId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * For getting the services offered by a branch
     *
     * @param branchId branch identifier
     * @return list of banking services
     */
    @ApiOperation(value = "View the services handled by the branch")
    @GetMapping("/branches/{id}/services")
    public ResponseEntity<List<BankingService>> getBranchServices(@PathVariable(name = "id") Integer branchId) {
        List<BankingService> bankingServices = branchService.getBankingServices(branchId);

        if (bankingServices == null) {
            bankingServices = new ArrayList<>();
        }
        return new ResponseEntity<>(bankingServices, HttpStatus.OK);
    }

    /**
     * For getting a particular service offered by a branch
     *
     * @param branchId branch identifier
     * @param serviceId service identifier
     * @return banking service
     */
    @ApiOperation(value = "View the particular services handled by the branch")
    @GetMapping("/branches/{id}/service/{serviceId}")
    public ResponseEntity<BankingService> getBranchService(@PathVariable(value = "id") Integer branchId,
                                                @PathVariable(value = "serviceId") Integer serviceId) {
        return new ResponseEntity<>(branchService.getBankingService(branchId, serviceId),HttpStatus.OK);
    }

    /**
     * Adds a service to the list of existing services served by a branch
     *
     * @param branchId branch identifier
     * @param bankingServiceModel banking service model
     * @return branch with updated services
     */
    @ApiOperation(value = "Add a new service to the services a particular branch servers")
    @PutMapping("/branches/{id}/service")
    public ResponseEntity<Branch> addBranchService(@PathVariable(value = "id") Integer branchId,
                                           @Valid @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        return new ResponseEntity<>(branchService.addBankingService(branchId, bankingService),HttpStatus.OK);
    }

    /**
     * Gets list of multi counter services offered by a branch
     *
     * @param branchId branch identifier
     * @return list of multi counter services
     */
    @ApiOperation(value = "View the multi-counter services handled by branch")
    @GetMapping("/branches/{id}/multipleCounterServices")
    public ResponseEntity<List<MultiCounterBankingService>> getBranchMultiCounterServices(@PathVariable(value = "id") Integer branchId) {
        List<MultiCounterBankingService> multiCounterBankingServices = branchService.getMultiCounterBankingServices(branchId);

        if (multiCounterBankingServices == null) {
            multiCounterBankingServices = new ArrayList<>();
        }
        return new ResponseEntity<>(multiCounterBankingServices, HttpStatus.OK);
    }

    /**
     * Gets a particular multi counter service offered by a branch
     *
     * @param branchId branch identifier
     * @param serviceId service identifier
     * @return multi counter service
     * @throws ResourceNotFoundException when the service is not served by  branch
     */
    @ApiOperation(value = "View a particular multi-counter service handled by branch")
    @GetMapping("/branches/{id}/multipleCounterServices/{serviceId}")
    public ResponseEntity<MultiCounterBankingService> getBranchMultiCounterService(@PathVariable(value = "id") Integer branchId,
                                                                        @PathVariable(value = "serviceId") Integer serviceId) {
        return new ResponseEntity<>(branchService.getMultiCounterBankingService(branchId, serviceId), HttpStatus.OK);
    }

    /**
     * Adds a multi counter service to the multi counter services offered by a branch
     *
     * @param branchId branch identifier
     * @param multiCounterBankingServiceModel multi counter banking service model
     * @return branch with added multi counter service
     * @throws ResourceNotFoundException when branch does not exist
     */
    @ApiOperation(value = "Add a new multi-counter service to the services a particular branch servers")
    @PutMapping("/branches/{id}/multipleCounterServices")
    public ResponseEntity<Branch> addBranchMultiCounterService(@PathVariable(value = "id") Integer branchId,
                                               @Valid @RequestBody MultiCounterBankingServiceModel multiCounterBankingServiceModel) {
        MultiCounterBankingService multiCounterBankingService = multiCounterBankingServiceModel.convertToSubEntity();
        return new ResponseEntity<>(branchService.addMultiCounterBankingService(branchId, multiCounterBankingService), HttpStatus.OK);
    }

    /**
     * Gets the list of counters present in the branch
     *
     * @param branchId branch identifier
     * @return list of counters present in the branch
     * @throws ResourceNotFoundException when branch does not exist or
     * no counter is present in branch
     */
    @ApiOperation(value = "View the counters present in a branch")
    @GetMapping("/branches/{id}/counters")
    public ResponseEntity<List<Counter>> getBranchCounters(@PathVariable(value = "id") Integer branchId) {
        List<Counter> counters = branchService.getCounters(branchId);

        if (counters == null) {
            counters = new ArrayList<>();
        }
        return new ResponseEntity<>(counters,HttpStatus.OK);
    }

    /**
     * Gets a particular counter present in the branch
     *
     * @param branchId branch identifier
     * @param counterId counter identifier
     * @return counter with the id passed
     * @throws ResourceNotFoundException when branch does not exist or
     * counter with passed id is not present in branch
     */
    @ApiOperation(value = "View a particular counter present in a branch")
    @GetMapping("/branches/{id}/counters/{counterId}")
    public ResponseEntity<Counter> getBranchCounter(@PathVariable(value = "id") Integer branchId,
                                         @PathVariable(value = "counterId") Integer counterId) {
        return new ResponseEntity<>(branchService.getCounter(branchId, counterId), HttpStatus.OK);
    }

}
