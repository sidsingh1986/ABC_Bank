package com.abc.bank.abc.DtoModels;

import com.abc.bank.abc.Enums.ServiceProcessingType;
import lombok.Data;

@Data
public abstract class BankingServiceInterface {

    ServiceProcessingType serviceProcessingType;
}
