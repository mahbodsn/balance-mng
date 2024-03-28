package com.barook.balancemng;

import com.barook.balancemng.entity.WalletEntity;
import com.barook.balancemng.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class BalanceManagementApplication implements ApplicationRunner {

	@Autowired
	private WalletRepository walletRepository;

	public static void main(String[] args) {
		SpringApplication.run(BalanceManagementApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("going to init some data in db");
		var wallet1 = new WalletEntity();
		wallet1.setUserId(UUID.randomUUID().toString());
		wallet1.setBalance(BigDecimal.ZERO);

		var wallet2 = new WalletEntity();
		wallet2.setBalance(BigDecimal.ZERO);
		wallet2.setUserId(UUID.randomUUID().toString());

		walletRepository.saveAll(List.of(wallet1, wallet2));

		log.info("wallet1 user-id is -> {}", wallet1.getUserId());
		log.info("wallet2 user-id is -> {}", wallet2.getUserId());
	}
}
