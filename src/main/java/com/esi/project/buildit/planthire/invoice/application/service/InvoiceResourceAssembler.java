package com.esi.project.buildit.planthire.invoice.application.service;

import com.esi.project.buildit.planthire.invoice.application.dto.InvoiceDto;
import com.esi.project.buildit.planthire.invoice.domain.model.Invoice;
import com.esi.project.buildit.planthire.invoice.rest.InvoiceController;
import com.esi.project.buildit.planthire.procurement.application.assembler.PurchaseOrderAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class InvoiceResourceAssembler extends ResourceAssemblerSupport<Invoice, InvoiceDto> {

	@Autowired
	PurchaseOrderAssembler assembler;

	public InvoiceResourceAssembler() {
		super(InvoiceController.class, InvoiceDto.class);
	}

	@Override
	public InvoiceDto toResource(Invoice entity) {
		InvoiceDto invoiceDto = createResourceWithId(entity.getId(), entity);
		invoiceDto.set_id(entity.getId());
		invoiceDto.setDueDate(entity.getDueDate());
		invoiceDto.setInvoiceNumber(entity.getInvoiceNumber());
		invoiceDto.setIssuedDate(entity.getIssuedDate());
		invoiceDto.setPaidDate(entity.getPaidDate());
		invoiceDto.setStatus(entity.getStatus());
		invoiceDto.setTotal(entity.getTotal());
		return invoiceDto;
	}

}
