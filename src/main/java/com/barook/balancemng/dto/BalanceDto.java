package com.barook.balancemng.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BalanceDto(String userId, BigDecimal balance, LocalDateTime createdAt) {
}
