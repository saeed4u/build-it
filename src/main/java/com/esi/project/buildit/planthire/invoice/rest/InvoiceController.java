package com.esi.project.buildit.planthire.invoice.rest;

import com.esi.project.buildit.planthire.common.rest.Response;
import com.esi.project.buildit.planthire.invoice.application.dto.InvoiceDto;
import com.esi.project.buildit.planthire.invoice.application.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

	@Autowired
	InvoiceService invoiceService;

	Logger logger = LoggerFactory.getLogger(InvoiceController.class);

	@PostMapping
	public InvoiceDto receiveInvoice(@RequestBody InvoiceDto invoiceDto) {
		InvoiceDto response = invoiceService.receiveInvoice(invoiceDto);
		logger.info("Response {}",response);
		return response;
	}



	@PutMapping("/{id}")
	public InvoiceDto payInvoice(@PathVariable  Long id) throws Exception {
		return invoiceService.invoicePaid(id);
	}


}
