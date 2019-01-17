package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Models.Branch;
import com.abc.bank.abc.Models.MultiCounterBankingService;
import com.abc.bank.abc.Services.BranchService;
import com.abc.bank.abc.Services.MultiCounterServicesService;
import com.abc.bank.abc.Services.ServicesSevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Branch", description = "Operations pertaining to the branch of a bank")
@RestController
public class BranchController {

    @Autowired
    BranchService branchService;

    @Autowired
    ServicesSevice servicesService;

    @Autowired
    MultiCounterServicesService multiCounterServicesService;

    /**
     * For getting all the branches registered in the system
     *
     * @return list of all branches
     */
    @ApiOperation(value = "View all branches of bank")
    @GetMapping("/branches")
    public List<Branch> getBranches() {
        return branchService.getAllBranches();
    }

    /**
     * For getting a specific branch
     *
     * @param branchId
     * @return branch if existing null otherwise
     */
    @ApiOperation(value = "View a particular branches of bank")
    @GetMapping("/branches/{id}")
    public Branch getBranch(@PathVariable(value = "id of the branch") Integer branchId) {
        return branchService.getBranch(branchId);
    }

    /**
     * For creating a new branch
     *
     * @param branch
     * @return new branch instance
     */
    @ApiOperation(value = "Creates a new branch of the bank")
    @PostMapping("/branches")
    public Branch createBranch(@RequestBody Branch branch) {
        return branchService.createNewBranch(branch);
    }

    @ApiOperation(value = "View the services handled by the branch")
    @GetMapping("/branches/{id}/services")
    public List<BankingService> getBranchServices(@PathVariable(name = "id of the branch") Integer branchId) {
        return branchService.getBankingServices(branchId);
    }

    @ApiOperation(value = "View the particular services handled by the branch")
    @GetMapping("/branches/{id}/service/{serviceId}")
    public BankingService getBranchService(@PathVariable(value = "id of the branch") Integer branchId,
                                           @PathVariable(value = "service Id to be viewed") Integer serviceId) {
        return branchService.getService(branchId, serviceId);
    }

    @ApiOperation(value = "Add a new service to the services a particular branch servers")
    @PostMapping("/branches/{id}/service/{serviceId}")
    public Branch addBranchService(@PathVariable(value = "id of the branch") Integer branchId,
                                           @PathVariable(value = "id of the service to be added") Integer serviceId) {
        BankingService bankingService = servicesService.getService(serviceId);
        return branchService.addService(branchId, bankingService);
    }

    @ApiOperation(value = "View the multi-counter services handled by branch")
    @GetMapping("/branches/{id}/multipleCounterServices")
    public List<MultiCounterBankingService> getBranchMultiCounterServices(@PathVariable(value = "id of the branch") Integer branchId) {
        return branchService.getMultiCounterBankingServices(branchId);
    }

    @ApiOperation(value = "View a particular multi-counter service handled by branch")
    @GetMapping("/branches/{id}/multipleCounterServices/{serviceId}")
    public MultiCounterBankingService getBranchMultiCounterService(@PathVariable(value = "id of the branch") Integer branchId,
                                                                   @PathVariable(value = "Id of the service to be viewed") Integer serviceId) {
        return branchService.getMultiCounterService(branchId, serviceId);
    }

    @ApiOperation(value = "Add a new multi-counter service to the services a particular branch servers")
    @PostMapping("/branches/{id}/multipleCounterServices/{serviceId}")
    public Branch addBranchMultiCounterService(@PathVariable(value = "id of the branch") Integer branchId,
                                               @PathVariable(value = "id of the service to be added") Integer serviceId) {
        MultiCounterBankingService multiCounterBankingService = multiCounterServicesService.getMultiCounterService(serviceId);
        return branchService.addMultiCounterService(branchId, multiCounterBankingService);
    }
}
