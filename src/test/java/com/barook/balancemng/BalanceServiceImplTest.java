package com.barook.balancemng;

import com.barook.balancemng.entity.TransactionEntity;
import com.barook.balancemng.entity.WalletEntity;
import com.barook.balancemng.repository.TransactionRepository;
import com.barook.balancemng.repository.WalletRepository;
import com.barook.balancemng.service.impl.BalanceServiceImpl;
import com.barook.balancemng.service.mapper.WalletServiceMapper;
import com.barook.balancemng.service.transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class BalanceServiceImplTest {

	@Autowired
	BalanceServiceImpl balanceService;

	@MockBean
	WalletRepository walletRepository;

	@MockBean
	TransactionService transactionService;

	@Test
	void addBalance_walletExists_balanceIncreaseAndReferenceNumberCreated() {
		var wallet = new WalletEntity();
		wallet.setUserId(UUID.randomUUID().toString());
		wallet.setBalance(BigDecimal.ZERO);
		when(walletRepository.findFirstByUserId(any())).thenReturn(Optional.of(wallet));
		var transaction = new TransactionEntity();
		transaction.setReferenceNumber(UUID.randomUUID().toString());
		when(transactionService.initTransaction(any(), any())).thenReturn(transaction);

		var result = balanceService.addBalance(wallet.getUserId(), BigDecimal.TEN);

		assertThat(result).isNotBlank();

		verify(transactionService, times(1)).initTransaction(wallet.getUserId(), BigDecimal.TEN);
	}

	@TestConfiguration
	@ComponentScan(basePackageClasses = {BalanceServiceImpl.class, WalletRepository.class, TransactionRepository.class,
			WalletServiceMapper.class},
			useDefaultFilters = false,
			includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
					classes = {BalanceServiceImpl.class, WalletRepository.class, TransactionRepository.class,
							WalletServiceMapper.class}))
	static class Configuration {

	}

}
