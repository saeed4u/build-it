package com.esi.project.buildit.planthire.procurement.application.assembler;

import com.esi.project.buildit.planthire.procurement.application.dto.PlantInventoryEntryDTO;
import com.esi.project.buildit.planthire.procurement.domain.model.PlantInventoryEntry;
import com.esi.project.buildit.planthire.procurement.web.controller.PlantInventoryEntryController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class PlantInventoryEntryAssembler extends ResourceAssemblerSupport<PlantInventoryEntry, PlantInventoryEntryDTO> {

	public PlantInventoryEntryAssembler() {
		super(PlantInventoryEntryController.class, PlantInventoryEntryDTO.class);
	}

	@Override
	public PlantInventoryEntryDTO toResource(PlantInventoryEntry entity) {
		PlantInventoryEntryDTO dto = createResourceWithId(entity.getHref(), entity);
		dto.setName(entity.getName());
		dto.setHref(entity.getHref());
		dto.set_id(entity.getId());
		return dto;
	}
}
