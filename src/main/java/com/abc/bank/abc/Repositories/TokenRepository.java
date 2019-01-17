package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
}
