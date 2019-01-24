package com.abc.bank.abc.DtoModels;

import com.abc.bank.abc.DtoModels.BankingServiceInterface;
import com.abc.bank.abc.Enums.TokenServiceStatus;
import com.abc.bank.abc.Models.BaseBankingService;
import lombok.Data;


@Data
public class ServicesPlaceholder {

    BankingServiceInterface service;

    int orderOfService;

    TokenServiceStatus tokenServiceStatus;
}
