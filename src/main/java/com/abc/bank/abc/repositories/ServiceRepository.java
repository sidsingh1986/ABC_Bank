package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.BankingService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<BankingService, Integer> {
}
