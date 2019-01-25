package com.abc.bank.abc.ViewModels;

import com.abc.bank.abc.Enums.ServiceProcessingType;
import lombok.Data;

@Data
public abstract class BankingServiceInterface {

    ServiceProcessingType serviceProcessingType;
}
