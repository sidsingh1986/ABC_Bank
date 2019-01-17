package com.abc.bank.abc.Models;

import lombok.Data;

import java.util.List;

@Data
public class ServicesPlaceholder {

    List<BankingService> bankingServices;
    List<MultiCounterBankingService> multiCounterBankingServices;
}
