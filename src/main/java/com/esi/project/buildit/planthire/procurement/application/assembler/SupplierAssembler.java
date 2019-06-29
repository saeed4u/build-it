package com.esi.project.buildit.planthire.procurement.application.assembler;

import com.esi.project.buildit.planthire.procurement.application.dto.SupplierDTO;
import com.esi.project.buildit.planthire.procurement.domain.model.Supplier;
import com.esi.project.buildit.planthire.procurement.web.controller.SupplierController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class SupplierAssembler extends ResourceAssemblerSupport<Supplier, SupplierDTO> {
	/**
	 * Creates a new {@link ResourceAssemblerSupport} using the given controller class and resource type.
	 */
	public SupplierAssembler() {
		super(SupplierController.class, SupplierDTO.class);
	}

	@Override
	public SupplierDTO toResource(Supplier entity) {
		SupplierDTO dto = createResourceWithId(entity.getId(), entity);
		dto.set_id(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
}
