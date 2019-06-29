package com.esi.project.buildit.planthire.invoice.domain.model;

import com.esi.project.buildit.planthire.procurement.domain.model.PurchaseOrder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Invoice {

	@Id
	@GeneratedValue
	Long id;

	@Column(unique = true, nullable = false)
	String invoiceNumber;

	BigDecimal total;

	@OneToOne(optional = false)
	PurchaseOrder purchaseOrder;

	LocalDate issuedDate;

	LocalDate paidDate;

	LocalDate dueDate;


	@Enumerated(value = EnumType.STRING)
	InvoiceStatus status;

	public Invoice(String invoiceNumber, BigDecimal total, PurchaseOrder purchaseOrder, LocalDate issuedDate, LocalDate paidDate, LocalDate dueDate, InvoiceStatus status) {
		this.invoiceNumber = invoiceNumber;
		this.total = total;
		this.purchaseOrder = purchaseOrder;
		this.issuedDate = issuedDate;
		this.paidDate = paidDate;
		this.dueDate = dueDate;
		this.status = status;
	}

	public Invoice() {
	}

	@Override
	public String toString() {
		return "Invoice{" +
				"id=" + id +
				", invoiceNumber='" + invoiceNumber + '\'' +
				", total=" + total +
				", issuedDate=" + issuedDate +
				", paidDate=" + paidDate +
				", dueDate=" + dueDate +
				", status=" + status +
				'}';
	}
}
