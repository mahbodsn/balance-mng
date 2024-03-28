package com.barook.balancemng.service.mapper;

import com.barook.balancemng.dto.BalanceDto;
import com.barook.balancemng.entity.WalletEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletServiceMapper {

	BalanceDto toBalanceDto(WalletEntity wallet);

}
