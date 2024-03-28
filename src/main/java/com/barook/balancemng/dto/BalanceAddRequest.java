package com.barook.balancemng.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BalanceAddRequest(@NotNull BigDecimal amount) {
}
