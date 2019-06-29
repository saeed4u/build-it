package com.esi.project.buildit.planthire.procurement.domain.model;


import com.esi.project.buildit.planthire.common.domain.enums.POExtensionStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class POExtension {

	@Id
	@GeneratedValue
	Long id;

	LocalDate endDate;

	@Enumerated(EnumType.STRING)
	POExtensionStatus status;

	@ManyToOne(optional = false)
	PurchaseOrder purchaseOrder;


	public POExtension(LocalDate endDate, POExtensionStatus status) {
		this.endDate = endDate;
		this.status = status;
	}

	public POExtension() {
	}

	@Override
	public String toString() {
		return "POExtension{" +
				"id=" + id +
				", endDate=" + endDate +
				", status=" + status +
				'}';
	}
}
