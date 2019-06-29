package com.esi.project.buildit.planthire.procurement.application.dto;

import com.esi.project.buildit.planthire.common.domain.enums.RentItPurchaseOrderStatus;
import com.esi.project.buildit.planthire.invoice.application.dto.InvoiceDto;
import lombok.Data;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
public class PurchaseOrderDTO extends ResourceSupport {

	String href;

	RentItPurchaseOrderStatus status;

	Link link;

	InvoiceDto invoiceDto;

}
