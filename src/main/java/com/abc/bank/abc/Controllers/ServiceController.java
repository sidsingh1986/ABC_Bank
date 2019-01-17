package com.abc.bank.abc.Controllers;

import com.abc.bank.abc.Models.BankingService;
import com.abc.bank.abc.Services.ServicesSevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Service", description = "Operations pertaining to the Service")
@RestController
public class ServiceController {

    @Autowired
    ServicesSevice servicesService;

    @ApiOperation(value = "Create a new banking services")
    @PostMapping("/service")
    public BankingService createService(@RequestBody BankingService bankingService) {
        return servicesService.createService(bankingService);
    }

    @ApiOperation(value = "View all banking services")
    @GetMapping("/services")
    public List<BankingService> getServices() {
        return servicesService.getServices();
    }

    @ApiOperation(value = "View a particular banking service")
    @GetMapping("/services/{serviceId}")
    public BankingService getService(@PathVariable(value = "serviceId") Integer serviceId) {
        return servicesService.getService(serviceId);
    }
}
