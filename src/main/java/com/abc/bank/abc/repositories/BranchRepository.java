package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.BankingService;
import com.abc.bank.abc.datamodels.Branch;
import com.abc.bank.abc.datamodels.Counter;
import com.abc.bank.abc.datamodels.MultiCounterBankingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
}
