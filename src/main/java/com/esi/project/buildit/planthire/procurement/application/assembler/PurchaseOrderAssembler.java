package com.esi.project.buildit.planthire.procurement.application.assembler;

import com.esi.project.buildit.planthire.invoice.application.service.InvoiceResourceAssembler;
import com.esi.project.buildit.planthire.invoice.domain.model.Invoice;
import com.esi.project.buildit.planthire.invoice.domain.repository.InvoiceRepository;
import com.esi.project.buildit.planthire.procurement.application.dto.PurchaseOrderDTO;
import com.esi.project.buildit.planthire.procurement.domain.model.PurchaseOrder;
import com.esi.project.buildit.planthire.procurement.web.controller.PurchaseOrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderAssembler extends ResourceAssemblerSupport<PurchaseOrder, PurchaseOrderDTO> {

	@Autowired
	InvoiceResourceAssembler invoiceResourceAssembler;

	@Autowired
	InvoiceRepository repository;

	public PurchaseOrderAssembler() {
		super(PurchaseOrderController.class, PurchaseOrderDTO.class);
	}

	@Override
	public PurchaseOrderDTO toResource(PurchaseOrder entity) {
		PurchaseOrderDTO dto = createResourceWithId(entity.getHref(), entity);

		dto.setHref(entity.getHref());
		dto.setStatus(entity.getStatus());
		Invoice invoice = repository.findByPurchaseOrderHref(entity.getHref());
		if (invoice != null) {
			dto.setInvoiceDto(invoiceResourceAssembler.toResource(invoice));
		} else {
			List<Invoice> invoices = repository.findAll();
			if (!invoices.isEmpty()) {
				dto.setInvoiceDto(invoiceResourceAssembler.toResource(invoices.get(0)));
			}
		}
		return dto;
	}
}
