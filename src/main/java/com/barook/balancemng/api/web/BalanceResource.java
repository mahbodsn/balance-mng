package com.barook.balancemng.api.web;

import com.barook.balancemng.dto.BalanceAddRequest;
import com.barook.balancemng.dto.BalanceDto;
import com.barook.balancemng.dto.ReferenceNumber;
import com.barook.balancemng.service.BalanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class BalanceResource {

	private final BalanceService balanceService;

	@GetMapping(path = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BalanceDto> getWalletBalance(@RequestHeader("User-Id") String userId) {
		var balance = balanceService.getBalance(userId);
		return ResponseEntity.ok(balance);
	}

	@PostMapping(path = "/add-balance", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReferenceNumber> addBalance(@Valid @RequestBody BalanceAddRequest request,
													  @RequestHeader("User-Id") String userId) {
		var balance = balanceService.addBalance(userId, request.amount());
		return ResponseEntity.ok(new ReferenceNumber(balance));
	}

}
