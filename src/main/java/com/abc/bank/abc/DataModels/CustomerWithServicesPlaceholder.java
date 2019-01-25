package com.abc.bank.abc.DataModels;

import com.abc.bank.abc.ViewModels.ServicesPlaceholder;
import lombok.Data;

@Data
public class CustomerWithServicesPlaceholder {
    private Customer customer;
    private ServicesPlaceholder servicesPlaceholder;
}
