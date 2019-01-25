package com.abc.bank.abc.Repositories;

import com.abc.bank.abc.DataModels.TokenService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenServiceRepository extends JpaRepository<TokenService, Integer> {
}
