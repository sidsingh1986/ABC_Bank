package com.abc.bank.abc.Controllers;

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

    @ApiOperation(value = "Create a new banking services")
    @PostMapping("/services")
    public BankingServiceModel createService(@Valid @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        return servicesService.createService(bankingService).convertToDTO();
    }

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

    @ApiOperation(value = "Gets a particular banking service")
    @GetMapping("/services/{serviceId}")
    public void getService(@PathVariable(value = "serviceId") Integer serviceId) {
        servicesService.getService(serviceId);
    }

    @ApiOperation(value = "Deletes a particular banking service")
    @DeleteMapping("/services/{serviceId}")
    public void deleteService(@PathVariable(value = "serviceId") Integer serviceId) {
        servicesService.deleteService(serviceId);
    }

    @ApiOperation(value = "Updates a particular banking service")
    @PutMapping("/services")
    public void updateService( @Valid @RequestBody BankingServiceModel bankingServiceModel) {
        BankingService bankingService = bankingServiceModel.convertToEntity();
        servicesService.updateService(bankingService);
    }
}
