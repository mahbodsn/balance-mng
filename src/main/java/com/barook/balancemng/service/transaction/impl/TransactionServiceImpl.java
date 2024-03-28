package com.barook.balancemng.service.transaction.impl;

import com.barook.balancemng.entity.TransactionEntity;
import com.barook.balancemng.repository.TransactionRepository;
import com.barook.balancemng.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository repository;

	@Override
	public TransactionEntity initTransaction(String userId, BigDecimal amount) {
		TransactionEntity transaction = new TransactionEntity();
		transaction.setUserId(userId);
		transaction.setAmount(amount);
		transaction.setReferenceNumber(UUID.randomUUID().toString());
		return repository.save(transaction);
	}
}
