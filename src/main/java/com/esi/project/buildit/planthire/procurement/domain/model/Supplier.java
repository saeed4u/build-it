package com.esi.project.buildit.planthire.procurement.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Supplier {
	@Id
	@GeneratedValue
	Long id;

	@Column(nullable = false)
	String name;

	public Supplier(String name) {
		this.name = name;
	}

	public Supplier() {
	}
}
