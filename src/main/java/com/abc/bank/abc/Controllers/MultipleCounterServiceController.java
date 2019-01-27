package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.DataModels.MultiCounterBankingService;
import com.abc.bank.abc.Exceptions.ResourceNotFoundException;
import com.abc.bank.abc.Services.MultiCounterServicesService;
import com.abc.bank.abc.ViewModels.MultiCounterBankingServiceModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Multi Counter Service", description = "Operations pertaining to the Multi Counter Service")
@RestController
public class MultipleCounterServiceController {

    @Autowired
    MultiCounterServicesService multiCounterServicesService;

    /**
     * For creating a Multi Counter Banking Service
     *
     * @param multiCounterBankingService multi counter banking service instance
     * @return created Multi counter banking service instance
     */
    @ApiOperation(value = "Create a new multi counter services")
    @PostMapping("/multipleCounterServices")
    public MultiCounterBankingService createMultiCounterService(@Valid @RequestBody MultiCounterBankingService multiCounterBankingService) {
        return multiCounterServicesService.createMultiCounterService(multiCounterBankingService);
    }

    /**
     * For getting the list of all Multi counter Banking services in the system
     *
     * @return list of all Multi counter banking services
     */
    @ApiOperation(value = "View all multi counter services")
    @GetMapping("/multipleCounterServices")
    public List<MultiCounterBankingService> getMultiCounterServices() {
        return multiCounterServicesService.getMultiCounterServices();
    }

    /**
     * For getting a particular Multi counter Banking Service
     *
     * @param serviceId service identifier
     * @return Multi counter service with service Id
     * @throws ResourceNotFoundException if the Banking service for the id is not found
     */
    @ApiOperation(value = "View a specific multi counter service")
    @GetMapping("/multipleCounterServices/{serviceId}")
    public MultiCounterBankingService getMultiCounterService(@PathVariable(value = "serviceId") Integer serviceId) {
        return multiCounterServicesService.getMultiCounterService(serviceId);
    }

    /**
     * For updating a Multi Counter Banking service or creating it if the Multi counter Banking service does not exists
     *
     * @param multiCounterBankingServiceModel multi counter banking service model to be updated
     */
    @ApiOperation(value = "update a specific multi counter service")
    @PutMapping("/multipleCounterServices")
    public void updateMultiCounterService(@Valid @RequestBody MultiCounterBankingServiceModel multiCounterBankingServiceModel) {
        multiCounterServicesService.updateMultiCounterService(multiCounterBankingServiceModel.convertToEntity());
    }

    /**
     * For deleting a particular Multi Counter Banking Service
     *
     * @param serviceId service identifier
     */
    @ApiOperation(value = "Delete a specific multi counter service")
    @DeleteMapping("/multipleCounterServices/{serviceId}")
    public void deleteMultiCounterService(@PathVariable(value = "serviceId") Integer serviceId) {
        multiCounterServicesService.deleteMultiCounterService(serviceId);
    }

}
