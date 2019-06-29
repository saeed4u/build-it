package com.esi.project.buildit.planthire.procurement.application.service;

import com.esi.project.buildit.planthire.common.application.exception.ResourceNotFoundException;
import com.esi.project.buildit.planthire.procurement.application.assembler.SupplierAssembler;
import com.esi.project.buildit.planthire.procurement.application.dto.SupplierDTO;
import com.esi.project.buildit.planthire.procurement.domain.model.Supplier;
import com.esi.project.buildit.planthire.procurement.domain.repository.SupplierRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

	private SupplierRepo repo;
	private SupplierAssembler assembler;

	public SupplierService(SupplierRepo repo, SupplierAssembler assembler) {
		this.repo = repo;
		this.assembler = assembler;
	}

	public SupplierDTO createSupplier(String name) {
		Supplier supplier = new Supplier();
		supplier.setName(name);
		return assembler.toResource(repo.save(supplier));
	}

	public Supplier getSupplier(Long id) {
		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("No supplier with ID %s was found", id)));
	}

	public List<SupplierDTO> getAll() {
		return repo.findAll().stream().map((supplier -> assembler.toResource(supplier))).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public SupplierDTO getSupplierWithId(Long id) {
		return assembler.toResource(getSupplier(id));
	}


}
