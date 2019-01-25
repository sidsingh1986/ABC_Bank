package com.abc.bank.abc.ViewModels;

import com.abc.bank.abc.Enums.TokenServiceStatus;
import lombok.Data;


@Data
public class ServicesPlaceholder {

    BankingServiceInterface service;

    int orderOfService;

    TokenServiceStatus tokenServiceStatus;
}
