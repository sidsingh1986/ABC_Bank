package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.Models.BankingService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<BankingService, Integer> {
}
