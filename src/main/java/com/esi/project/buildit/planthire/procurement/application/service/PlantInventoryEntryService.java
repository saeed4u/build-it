package com.esi.project.buildit.planthire.procurement.application.service;

import com.esi.project.buildit.planthire.common.application.dto.BusinessPeriodDTO;
import com.esi.project.buildit.planthire.procurement.application.assembler.RentItToBuildItPlantInvEntryAssembler;
import com.esi.project.buildit.planthire.procurement.application.dto.PlantInventoryEntryDTO;
import com.esi.project.buildit.planthire.procurement.domain.model.PlantInventoryEntry;
import com.esi.project.buildit.planthire.procurement.domain.repository.PlantInventoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantInventoryEntryService {

	private PlantInventoryRepo repo;


	private RentItIntegrationService integrationService;

	private RentItToBuildItPlantInvEntryAssembler rentItToBuildItPlantInvEntryAssembler;

	public PlantInventoryEntryService(PlantInventoryRepo repo, RentItIntegrationService integrationService, RentItToBuildItPlantInvEntryAssembler rentItToBuildItPlantInvEntryAssembler) {
		this.repo = repo;
		this.integrationService = integrationService;
		this.rentItToBuildItPlantInvEntryAssembler = rentItToBuildItPlantInvEntryAssembler;
	}

	public PlantInventoryEntryDTO findPlantWithHref(String href){
		return rentItToBuildItPlantInvEntryAssembler.toResource(integrationService.fetchPlantEntry(href));
	}

	public List<PlantInventoryEntryDTO> queryRentitCatalog(String name, BusinessPeriodDTO periodDTO){
		 return integrationService.queryCatalog(name,periodDTO).getData().stream().map(entryDto -> rentItToBuildItPlantInvEntryAssembler.toResource(entryDto)).collect(Collectors.toList());
	}

	public Optional<PlantInventoryEntry> getPlantWithHref(String href){
		return repo.findById(href);
	}

	public PlantInventoryEntry createPlant(Long id,String href){
		PlantInventoryEntryDTO plantInventoryEntryDTO = findPlantWithHref(href);
		return repo.save(PlantInventoryEntry.of(href,id,plantInventoryEntryDTO.getName()));
	}


}
