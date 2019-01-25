package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.DataModels.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterRepository extends JpaRepository<Counter, Integer> {
}
