package com.barook.balancemng.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class WalletEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "balance")
	private BigDecimal balance;

	@CreatedDate
	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@LastModifiedDate
	@Column(name = "last_modification_date")
	private LocalDateTime lastModificationDate;

	@Version
	private Integer version;

}
