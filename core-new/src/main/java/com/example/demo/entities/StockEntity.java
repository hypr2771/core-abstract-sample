package com.example.demo.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "stock")
public class StockEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2120511417522816893L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "stock")
	private Set<ShoeEntity> shoesEntity;

	private Integer totalQuantity;

	@Column(name = "creation_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate creationDate;

}
