package com.barook.balancemng.service.transaction;

import com.barook.balancemng.entity.TransactionEntity;

import java.math.BigDecimal;

public interface TransactionService {

	TransactionEntity initTransaction(String userId, BigDecimal amount);

}
