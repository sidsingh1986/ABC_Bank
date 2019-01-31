package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
}
