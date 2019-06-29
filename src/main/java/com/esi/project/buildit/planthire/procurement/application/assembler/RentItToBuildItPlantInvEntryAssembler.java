package com.esi.project.buildit.planthire.procurement.application.assembler;

import com.esi.project.buildit.planthire.common.application.dto.MoneyDTO;
import com.esi.project.buildit.planthire.procurement.application.dto.PlantInventoryEntryDTO;
import com.esi.project.buildit.planthire.procurement.application.dto.RentItPlantInventoryEntryDTO;
import com.esi.project.buildit.planthire.procurement.web.controller.BaseController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class RentItToBuildItPlantInvEntryAssembler extends ResourceAssemblerSupport<RentItPlantInventoryEntryDTO, PlantInventoryEntryDTO> {

	public RentItToBuildItPlantInvEntryAssembler() {
		super(BaseController.class, PlantInventoryEntryDTO.class);
	}

	@Override
	public PlantInventoryEntryDTO toResource(RentItPlantInventoryEntryDTO entity) {
		PlantInventoryEntryDTO dto = createResourceWithId(entity.get_id(), entity);
		dto.setName(entity.getName());
		dto.setDesc(entity.getDescription());
		if (!entity.getLinks().isEmpty()) {
			dto.setHref(entity.getLinks().get(0).getHref());
		}
		dto.set_id(entity.get_id());
		dto.setPricePerDay(MoneyDTO.of(entity.getPrice()));
		dto.removeLinks();
		dto.getPricePerDay().removeLinks();
		return dto;
	}
}
