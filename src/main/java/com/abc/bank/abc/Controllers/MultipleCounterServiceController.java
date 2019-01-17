package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.Models.MultiCounterBankingService;
import com.abc.bank.abc.Services.MultiCounterServicesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Multi Counter Service", description = "Operations pertaining to the Multi Counter Service")
@RestController
public class MultipleCounterServiceController {

    @Autowired
    MultiCounterServicesService multiCounterServicesService;

    @ApiOperation(value = "Create a new multi counter services")
    @PostMapping("/multipleCounterServices")
    public MultiCounterBankingService createMultiCounterService(@RequestBody MultiCounterBankingService multiCounterBankingService) {
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

}
