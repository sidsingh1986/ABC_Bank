package com.abc.bank.abc.Models;

import com.abc.bank.abc.Enums.ServiceProcessingType;
import lombok.Data;

@Data
public abstract class BaseBankingService {

    ServiceProcessingType serviceProcessingType;
}
