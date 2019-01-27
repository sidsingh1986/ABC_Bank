package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.Exceptions.ResourceNotFoundException;
import com.abc.bank.abc.ViewModels.BankingServiceModel;
import com.abc.bank.abc.DataModels.BankingService;
import com.abc.bank.abc.Services.ServicesSevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value = "Service", description = "Operations pertaining to the Service")
@RestController
public class ServiceController {

    @Autowired
    ServicesSevice servicesService;

    /**
     * For creating a Banking Service
     *
     * @param bankingServiceModel banking service model instance to be created
     * @return created banking service instance
     */
    @ApiOperation(value = "Create a new banking services")
    @PostMapping("/services")
    public BankingServiceModel createService(@Valid @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        return servicesService.createService(bankingService).convertToDTO();
    }

    /**
     * For getting the list of all Banking services in the system
     *
     * @return list of all banking services
     */
    @ApiOperation(value = "View all banking services")
    @GetMapping("/services")
    public List<BankingServiceModel> getServices() {
        List<BankingServiceModel> bankingServiceRespons = new ArrayList<>();
        List<BankingService> bankingServices = servicesService.getServices();

        for(int index = 0; index < bankingServices.size(); index++) {
            bankingServiceRespons.add(bankingServices.get(index).convertToDTO());
        }
        return bankingServiceRespons;
    }

    /**
     * For getting a particular Banking Service
     *
     * @param serviceId service identifier
     * @return service with service Id
     * @throws ResourceNotFoundException if the Banking service for the id is not found
     */
    @ApiOperation(value = "Gets a particular banking service")
    @GetMapping("/services/{serviceId}")
    public BankingServiceModel getService(@PathVariable(value = "serviceId") Integer serviceId) {
        return servicesService.getService(serviceId).convertToDTO();
    }

    /**
     * For deleting a particular Banking Service
     *
     * @param serviceId service identifier
     */
    @ApiOperation(value = "Deletes a particular banking service")
    @DeleteMapping("/services/{serviceId}")
    public void deleteService(@PathVariable(value = "serviceId") Integer serviceId) {
        servicesService.deleteService(serviceId);
    }

    /**
     * For updating a Banking service or creating it if the Banking service does not exists
     *
     * @param bankingServiceModel banking service model to be updated
     */
    @ApiOperation(value = "Updates a particular banking service")
    @PutMapping("/services")
    public void updateService( @Valid @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        servicesService.updateService(bankingService);
    }
}
