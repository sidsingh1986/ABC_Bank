package com.abc.bank.abc.Models;

import com.abc.bank.abc.DtoModels.ServicesPlaceholder;
import lombok.Data;

@Data
public class CustomerWithServicesPlaceholder {
    private Customer customer;
    private ServicesPlaceholder servicesPlaceholder;
}
