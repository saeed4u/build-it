package com.esi.project.buildit.planthire.invoice.application.dto;

import com.esi.project.buildit.planthire.invoice.domain.model.InvoiceStatus;
import com.esi.project.buildit.planthire.procurement.application.dto.PurchaseOrderDTO;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceDto extends ResourceSupport {

	Long _id;

	String invoiceNumber;

	BigDecimal total;

	PurchaseOrderDTO purchaseOrder;

	InvoiceStatus status;

	LocalDate issuedDate;

	LocalDate paidDate;

	LocalDate dueDate;

	public InvoiceDto(String invoiceNumber, BigDecimal total, PurchaseOrderDTO purchaseOrder, InvoiceStatus status, LocalDate issuedDate, LocalDate paidDate, LocalDate dueDate) {
		this.invoiceNumber = invoiceNumber;
		this.total = total;
		this.purchaseOrder = purchaseOrder;
		this.status = status;
		this.issuedDate = issuedDate;
		this.paidDate = paidDate;
		this.dueDate = dueDate;
	}

	public InvoiceDto() {
	}
}
