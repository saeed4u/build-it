package com.esi.project.buildit.planthire.procurement.web.controller;

import com.esi.project.buildit.planthire.common.application.dto.RentItPOStatusUpdateDTO;
import com.esi.project.buildit.planthire.common.rest.Response;
import com.esi.project.buildit.planthire.procurement.application.dto.PurchaseOrderDTO;
import com.esi.project.buildit.planthire.procurement.application.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pos")
public class PurchaseOrderController {

	private PurchaseOrderService service;

	public PurchaseOrderController(PurchaseOrderService service) {
		this.service = service;
	}

	@GetMapping
	public List<PurchaseOrderDTO> getAllPOs(){
		return service.getAllPOs();
	}

	@PutMapping
	public Response updateDto(@RequestBody RentItPOStatusUpdateDTO statusUpdateDTO){
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.info("Request from rentit {}",statusUpdateDTO);
		return service.updateStatus(statusUpdateDTO.getHref(),statusUpdateDTO.getValue());
	}


}
