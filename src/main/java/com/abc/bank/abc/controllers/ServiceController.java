package com.abc.bank.abc.controllers;

import com.abc.bank.abc.exceptions.ResourceNotFoundException;
import com.abc.bank.abc.viewmodels.BankingServiceModel;
import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.services.ServicesSevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BankingService> createService(@Valid @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        return new ResponseEntity<>(servicesService.createService(bankingService), HttpStatus.CREATED);
    }

    /**
     * For getting the list of all Banking services in the system
     *
     * @return list of all banking services
     */
    @ApiOperation(value = "View all banking services")
    @GetMapping("/services")
    public ResponseEntity<List<BankingService>> getServices() {
        List<BankingService> bankingServices = servicesService.getServices();

        if (bankingServices == null) {
            bankingServices = new ArrayList<>();
        }
        return new ResponseEntity<>(bankingServices, HttpStatus.OK);
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
    public ResponseEntity<BankingService> getService(@PathVariable(value = "serviceId") Integer serviceId) {
        return new ResponseEntity<>(servicesService.getService(serviceId), HttpStatus.OK);
    }

    /**
     * For deleting a particular Banking Service
     *
     * @param serviceId service identifier
     */
    @ApiOperation(value = "Deletes a particular banking service")
    @DeleteMapping("/services/{serviceId}")
    public ResponseEntity<String> deleteService(@PathVariable(value = "serviceId") Integer serviceId) {
        servicesService.deleteService(serviceId);
        return new ResponseEntity<>("Deletion of Banking Service successful", HttpStatus.OK);
    }

    /**
     * For updating a Banking service or creating it if the Banking service does not exists
     *
     * @param bankingServiceModel banking service model to be updated
     */
    @ApiOperation(value = "Updates a particular banking service")
    @PutMapping("/services/{id}")
    public ResponseEntity<BankingService> updateService( @PathVariable("id") Integer serviceId, @Valid @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        return new ResponseEntity<>(servicesService.updateService(serviceId, bankingService), HttpStatus.OK);
    }
}
