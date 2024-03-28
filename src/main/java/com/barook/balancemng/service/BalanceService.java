package com.barook.balancemng.service;

import com.barook.balancemng.dto.BalanceDto;

import java.math.BigDecimal;

public interface BalanceService {

	BalanceDto getBalance(String userId);

	String addBalance(String userId, BigDecimal amount);
}
