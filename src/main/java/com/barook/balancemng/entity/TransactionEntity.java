package com.barook.balancemng.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions", indexes = {
		@Index(columnList = "reference_number", unique = true)
})
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class TransactionEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "reference_number")
	private String referenceNumber;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "amount")
	private BigDecimal amount;

	@CreatedDate
	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@LastModifiedDate
	@Column(name = "last_modification_date")
	private LocalDateTime lastModificationDate;

}
