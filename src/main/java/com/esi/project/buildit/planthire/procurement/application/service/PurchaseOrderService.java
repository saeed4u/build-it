package com.esi.project.buildit.planthire.procurement.application.service;

import com.esi.project.buildit.planthire.common.application.exception.ResourceNotFoundException;
import com.esi.project.buildit.planthire.common.domain.enums.RentItPurchaseOrderStatus;
import com.esi.project.buildit.planthire.common.rest.Response;
import com.esi.project.buildit.planthire.common.rest.Responses;
import com.esi.project.buildit.planthire.procurement.application.assembler.PurchaseOrderAssembler;
import com.esi.project.buildit.planthire.procurement.application.dto.PurchaseOrderDTO;
import com.esi.project.buildit.planthire.procurement.domain.model.PurchaseOrder;
import com.esi.project.buildit.planthire.procurement.domain.repository.PurchaseOrderRepo;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseOrderService {

	private PurchaseOrderRepo repo;

	private PurchaseOrderAssembler assembler;

	public PurchaseOrderService(PurchaseOrderRepo repo, PurchaseOrderAssembler assembler) {
		this.repo = repo;
		this.assembler = assembler;
	}

	@Transactional(readOnly = true)
	public List<PurchaseOrderDTO> getAllPOs() {
		return assembler.toResources(repo.findAll());
	}

	private PurchaseOrder getPO(String href) {
		return repo.findById(href).orElseThrow(() -> new ResourceNotFoundException(String.format("No PO with href %s", href)));
	}

	public PurchaseOrder createPO(String href, RentItPurchaseOrderStatus status) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setHref(href);
		purchaseOrder.setStatus(status);
		return repo.save(purchaseOrder);
	}

	@Transactional
	public Response<Object> updateStatus(String href, RentItPurchaseOrderStatus status) {

		PurchaseOrderDTO purchaseOrderDTO = assembler.toResource(updatePurchaseOrder(href, status));
		LoggerFactory.getLogger(this.getClass()).info("PurchaseOrderDTO {}", purchaseOrderDTO);
		return Responses.success();
	}

	public PurchaseOrder updatePurchaseOrder(String href, RentItPurchaseOrderStatus status) {
		PurchaseOrder purchaseOrder = getPO(href);

		purchaseOrder.setStatus(status);
		return save(purchaseOrder);
	}

	public PurchaseOrder save(PurchaseOrder purchaseOrder) {
		return repo.save(purchaseOrder);
	}




}
