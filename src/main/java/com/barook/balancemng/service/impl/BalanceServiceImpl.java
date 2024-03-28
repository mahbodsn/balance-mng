package com.barook.balancemng.service.impl;

import com.barook.balancemng.dto.BalanceDto;
import com.barook.balancemng.entity.WalletEntity;
import com.barook.balancemng.repository.WalletRepository;
import com.barook.balancemng.service.BalanceService;
import com.barook.balancemng.service.mapper.WalletServiceMapper;
import com.barook.balancemng.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

	private final WalletRepository repository;

	private final TransactionService transactionService;

	private final WalletServiceMapper mapper;

	public BalanceDto getBalance(String userId) {
		var wallet = getWalletByUserId(userId);
		return mapper.toBalanceDto(wallet);
	}

	@Override
	public String addBalance(String userId, BigDecimal amount) {
		var wallet = getWalletByUserId(userId);
		wallet.setBalance(amount.signum() > 0 ? wallet.getBalance().add(amount) :
				wallet.getBalance().subtract(amount.abs()));
		var updatedWallet = repository.save(wallet);
		log.info("updated wallet after adding balance -> {}", updatedWallet);
		return transactionService.initTransaction(userId, amount).getReferenceNumber();
	}

	private WalletEntity getWalletByUserId(String userId) {
		return repository.findFirstByUserId(userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

}
