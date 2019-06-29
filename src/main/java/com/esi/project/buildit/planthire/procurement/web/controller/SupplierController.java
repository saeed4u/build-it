package com.esi.project.buildit.planthire.procurement.web.controller;

import com.esi.project.buildit.planthire.procurement.application.dto.SupplierDTO;
import com.esi.project.buildit.planthire.procurement.application.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/suppliers")
public class SupplierController {

	private SupplierService service;

	public SupplierController(SupplierService service) {
		this.service = service;
	}

	@GetMapping
	public List<SupplierDTO> getAllSuppliers() {
		return service.getAll();
	}

	@PostMapping
	public SupplierDTO createSupplier(@RequestBody SupplierDTO supplierDTO){

		Logger logger = LoggerFactory.getLogger(SupplierController.class);
		logger.info("Supplier {}",supplierDTO);
		return service.createSupplier(supplierDTO.getName());
	}

	@GetMapping("/{id}")
	public SupplierDTO readOne(@PathVariable Long id) {
		return service.getSupplierWithId(id);
	}


}
