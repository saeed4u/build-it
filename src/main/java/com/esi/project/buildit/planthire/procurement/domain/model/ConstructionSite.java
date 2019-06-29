package com.esi.project.buildit.planthire.procurement.domain.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
public class ConstructionSite {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	@Column(nullable = false)
	String address;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ConstructionSite that = (ConstructionSite) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(address, that.address);
	}

	public ConstructionSite(String address) {
		this.address = address;
	}

	public ConstructionSite() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, address);
	}
}
