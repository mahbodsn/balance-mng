package com.barook.balancemng;

import com.barook.balancemng.dto.BalanceAddRequest;
import com.barook.balancemng.dto.BalanceDto;
import com.barook.balancemng.dto.ReferenceNumber;
import com.barook.balancemng.entity.WalletEntity;
import com.barook.balancemng.repository.TransactionRepository;
import com.barook.balancemng.repository.WalletRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@ImportTestcontainers({MysqlContainer.class})
class BalanceResourceIT {

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		walletRepository.deleteAll();
		transactionRepository.deleteAll();
	}

	@Test
	void addBalance_walletExists_increaseBalance() throws Exception {
		var wallet = new WalletEntity();
		wallet.setUserId(UUID.randomUUID().toString());
		wallet.setBalance(BigDecimal.ZERO);
		walletRepository.save(wallet);
		assertThat(transactionRepository.findAll()).isEmpty();

		var request = new BalanceAddRequest(new BigDecimal(1000));

		var mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/wallets/add-balance")
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.accept(MediaType.APPLICATION_JSON_VALUE)
								.content(objectMapper.writeValueAsString(request))
								.header("User-Id", wallet.getUserId())
				)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andReturn();
		var response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ReferenceNumber.class);

		assertThat(response.referenceNumber()).isNotBlank();

		var transaction = transactionRepository.findAll().getFirst();
		assertThat(transaction).isNotNull();
		assertThat(transaction.getAmount()).isNotNull();
		assertThat(transaction.getUserId()).isNotBlank();
		assertThat(transaction.getReferenceNumber()).isNotBlank();

		var updatedWallet = walletRepository.findAll().getFirst();
		assertThat(updatedWallet.getBalance()).isEqualByComparingTo(new BigDecimal(1000));
	}

	@Test
	void getBalance_userNotExists_returnError() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders.post("/wallets/add-balance")
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.accept(MediaType.APPLICATION_JSON_VALUE)
								.content(objectMapper.writeValueAsString(new BalanceAddRequest(new BigDecimal(10))))
								.header("User-Id", UUID.randomUUID().toString())
				)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	void getBalance_userExists_returnBalance() throws Exception {
		var wallet = new WalletEntity();
		wallet.setUserId(UUID.randomUUID().toString());
		wallet.setBalance(BigDecimal.ZERO);
		walletRepository.save(wallet);

		var mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.get("/wallets/balance")
								.header("User-Id", wallet.getUserId())
				)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andReturn();

		var response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BalanceDto.class);

		assertThat(response).isNotNull();
		assertThat(response.balance()).isNotNull();
		assertThat(response.userId()).isNotNull();
	}

}
