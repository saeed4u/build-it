package com.esi.project.buildit.planthire.invoice.application.service;

import com.esi.project.buildit.planthire.common.domain.enums.RentItPurchaseOrderStatus;
import com.esi.project.buildit.planthire.invoice.application.dto.InvoiceDto;
import com.esi.project.buildit.planthire.invoice.domain.model.Invoice;
import com.esi.project.buildit.planthire.invoice.domain.model.InvoiceStatus;
import com.esi.project.buildit.planthire.invoice.domain.repository.InvoiceRepository;
import com.esi.project.buildit.planthire.procurement.application.service.RentItIntegrationService;
import com.esi.project.buildit.planthire.procurement.domain.model.PurchaseOrder;
import com.esi.project.buildit.planthire.procurement.domain.repository.PurchaseOrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.esi.project.buildit.planthire.invoice.domain.model.InvoiceStatus.RECEIVED;

@Service
public class InvoiceService {

	@Autowired
	InvoiceRepository invoiceRepository;

	@Autowired
	PurchaseOrderRepo purchaseOrderRepository;

	@Autowired
	InvoiceResourceAssembler resourceAssembler;

	@Autowired
	RentItIntegrationService rentItIntegrationService;

	private Logger logger = LoggerFactory.getLogger(InvoiceService.class);

	public InvoiceDto receiveInvoice(InvoiceDto invoiceDto) {
		logger.info("Received create invoice {}", invoiceDto);
		String poHref = invoiceDto.getPurchaseOrder().getLink().getHref();
		PurchaseOrder purchaseOrder = purchaseOrderRepository.findByHrefContaining(poHref).orElse(null);
		if (purchaseOrder == null) {
			logger.info("Purchase order not found");
			return null;
		}

		if (purchaseOrder.getStatus() == RentItPurchaseOrderStatus.PAID) {
			logger.info("Purchase order has been paid: {}", purchaseOrder);
			return null;
		}

		Invoice invoice = invoiceRepository.save(new Invoice(invoiceDto.getInvoiceNumber(), invoiceDto.getTotal(), purchaseOrder,
				invoiceDto.getIssuedDate(), invoiceDto.getPaidDate(), invoiceDto.getDueDate(), RECEIVED));
		logger.info("Invoice received {}", invoice);
		return resourceAssembler.toResource(invoice);
	}

	public List<InvoiceDto> getAllInvoices() {
		return invoiceRepository.findAll().stream().map(invoice -> resourceAssembler.toResource(invoice)).collect(Collectors.toList());
	}

	public InvoiceDto invoicePaid(Long id) throws Exception {
		Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new Exception(String.format("Invoice with id %s not found", id)));
		invoice.setStatus(InvoiceStatus.PAID);
		invoice.setPaidDate(LocalDate.now());
		PurchaseOrder purchaseOrder = invoice.getPurchaseOrder();
		purchaseOrder.setStatus(RentItPurchaseOrderStatus.PAID);
		purchaseOrderRepository.save(purchaseOrder);
		rentItIntegrationService.sendPaymentNotice(resourceAssembler.toResource(invoice));
		return resourceAssembler.toResource(invoiceRepository.save(invoice));
	}


}
