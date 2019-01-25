package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.DataModels.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
}
