package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.DataModels.MultiCounterBankingService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultiCounterRepository extends JpaRepository<MultiCounterBankingService, Integer> {
}
