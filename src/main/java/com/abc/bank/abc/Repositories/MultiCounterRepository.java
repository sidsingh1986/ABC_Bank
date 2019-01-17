package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.Models.Customer;
import com.abc.bank.abc.Models.MultiCounterBankingService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultiCounterRepository extends JpaRepository<MultiCounterBankingService, Integer> {
}
