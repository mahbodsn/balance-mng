package com.barook.balancemng.repository;

import com.barook.balancemng.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

	Optional<WalletEntity> findFirstByUserId(String userId);

}
