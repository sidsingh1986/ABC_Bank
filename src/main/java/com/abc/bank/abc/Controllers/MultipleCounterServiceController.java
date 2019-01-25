package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.DataModels.MultiCounterBankingService;
import com.abc.bank.abc.Services.MultiCounterServicesService;
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

    @ApiOperation(value = "Create a new multi counter services")
    @PostMapping("/multipleCounterServices")
    public MultiCounterBankingService createMultiCounterService(@Valid @RequestBody MultiCounterBankingService multiCounterBankingService) {
        return multiCounterServicesService.createMultiCounterService(multiCounterBankingService);
    }

    @ApiOperation(value = "View all multi counter services")
    @GetMapping("/multipleCounterServices")
    public List<MultiCounterBankingService> getMultiCounterServices() {
        return multiCounterServicesService.getMultiCounterServices();
    }

    @ApiOperation(value = "View a specific multi counter service")
    @GetMapping("/multipleCounterServices/{serviceId}")
    public MultiCounterBankingService getMultiCounterService(@PathVariable(value = "serviceId") Integer serviceId) {
        return multiCounterServicesService.getMultiCounterService(serviceId);
    }

    @ApiOperation(value = "View a specific multi counter service")
    @PutMapping("/multipleCounterServices")
    public void updateMultiCounterService(@Valid @RequestBody MultiCounterBankingService multiCounterBankingService) {
        multiCounterServicesService.updateMultiCounterService(multiCounterBankingService);
    }

    @ApiOperation(value = "Delete a specific multi counter service")
    @DeleteMapping("/multipleCounterServices/{serviceId}")
    public void deleteMultiCounterService(@PathVariable(value = "serviceId") Integer serviceId) {
        multiCounterServicesService.deleteMultiCounterService(serviceId);
    }

}
