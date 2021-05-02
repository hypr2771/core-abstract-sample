package com.example.demo.entities;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "shoe")
public class ShoeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7073048837058601737L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	@Column(name = "name")
	private String name;

	@Column(name = "size")
	private BigInteger size;

	@Column(name = "color")
	private String color;
	
	@Column(name = "quantity")
	private BigInteger quantity;
	
	@ManyToOne
	@JoinColumn(name = "id_stock", referencedColumnName = "id")
	private StockEntity stock;


}
