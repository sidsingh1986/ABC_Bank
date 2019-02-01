package com.abc.bank.abc.controllers;

import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.MultiCounterBankingService;
import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.services.MultiCounterServicesService;
import com.abc.bank.abc.viewmodels.MultiCounterBankingServiceModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * @param multiCounterBankingServiceModel multi counter banking service instance
     * @return created Multi counter banking service instance
     */
    @ApiOperation(value = "Create a new multi counter services")
    @PostMapping("/multipleCounterServices")
    public ResponseEntity<MultiCounterBankingService> createMultiCounterService(@Valid @RequestBody MultiCounterBankingServiceModel multiCounterBankingServiceModel) {
        return new ResponseEntity<>(multiCounterServicesService.createMultiCounterService(multiCounterBankingServiceModel.convertToSubEntity()), HttpStatus.CREATED);
    }

    /**
     * For getting the list of all Multi counter Banking services in the system
     *
     * @return list of all Multi counter banking services
     */
    @ApiOperation(value = "View all multi counter services")
    @GetMapping("/multipleCounterServices")
    public ResponseEntity<List<MultiCounterBankingService>> getMultiCounterServices() {
        return new ResponseEntity<>(multiCounterServicesService.getMultiCounterServices(), HttpStatus.OK);
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
    public ResponseEntity<MultiCounterBankingService> getMultiCounterService(@PathVariable(value = "serviceId") Integer serviceId) {
        return new ResponseEntity<>(multiCounterServicesService.getMultiCounterService(serviceId), HttpStatus.OK);
    }

    /**
     * For updating a Multi Counter Banking service or creating it if the Multi counter Banking service does not exists
     *
     * @param multiCounterBankingServiceModel multi counter banking service model to be updated
     */
    @ApiOperation(value = "update a specific multi counter service")
    @PutMapping("/multipleCounterServices/{serviceId}")
    public ResponseEntity<MultiCounterBankingService> updateMultiCounterService(@PathVariable(value = "serviceId") Integer serviceId, @Valid @RequestBody MultiCounterBankingServiceModel multiCounterBankingServiceModel) {
        multiCounterServicesService.updateMultiCounterService(serviceId, multiCounterBankingServiceModel.convertToSubEntity());
        return new ResponseEntity<>(multiCounterServicesService.updateMultiCounterService(serviceId, multiCounterBankingServiceModel.convertToSubEntity()), HttpStatus.OK);
    }

    /**
     * For deleting a particular Multi Counter Banking Service
     *
     * @param serviceId service identifier
     */
    @ApiOperation(value = "Delete a specific multi counter service")
    @DeleteMapping("/multipleCounterServices/{serviceId}")
    public ResponseEntity<String> deleteMultiCounterService(@PathVariable(value = "serviceId") Integer serviceId) {
        multiCounterServicesService.deleteMultiCounterService(serviceId);
        return new ResponseEntity<>("Multi Counter Service deletion Successful", HttpStatus.OK);
    }
}
