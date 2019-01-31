package com.abc.bank.abc.repositories;

import com.abc.bank.abc.datamodels.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
}
