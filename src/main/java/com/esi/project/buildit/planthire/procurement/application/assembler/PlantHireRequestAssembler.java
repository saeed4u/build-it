package com.esi.project.buildit.planthire.procurement.application.assembler;

import com.esi.project.buildit.planthire.common.application.service.BusinessPeriodAssembler;
import com.esi.project.buildit.planthire.common.application.service.EmployeeAssembler;
import com.esi.project.buildit.planthire.common.application.service.MoneyAssembler;
import com.esi.project.buildit.planthire.procurement.application.dto.CommentDTO;
import com.esi.project.buildit.planthire.procurement.application.dto.PlantHireRequestDTO;
import com.esi.project.buildit.planthire.procurement.domain.model.PlantHireRequest;
import com.esi.project.buildit.planthire.procurement.web.controller.PlantHireRequestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class PlantHireRequestAssembler extends ResourceAssemblerSupport<PlantHireRequest, PlantHireRequestDTO> {

	@Autowired
	CommentAssembler commentAssembler;

	@Autowired
	ConstructionSiteAssembler constructionSiteAssembler;

	@Autowired
	BusinessPeriodAssembler businessPeriodAssembler;

	@Autowired
	SupplierAssembler supplierAssembler;

	@Autowired
	PlantInventoryEntryAssembler plantInventoryEntryAssembler;

	@Autowired
	MoneyAssembler moneyAssembler;

	@Autowired
	PurchaseOrderAssembler purchaseOrderAssembler;

	@Autowired
	EmployeeAssembler employeeAssembler;

	public PlantHireRequestAssembler() {
		super(PlantHireRequestController.class, PlantHireRequestDTO.class);

	}

	@Override
	public PlantHireRequestDTO toResource(PlantHireRequest plantHireRequest) {
		PlantHireRequestDTO plantHireRequestDTO = createResourceWithId(plantHireRequest.getId(), plantHireRequest);

		plantHireRequestDTO.set_id(plantHireRequest.getId());

		plantHireRequestDTO.setRentalPeriod(businessPeriodAssembler.toResource(plantHireRequest.getRentalPeriod()));

		plantHireRequestDTO.setPlant(plantInventoryEntryAssembler.toResource(plantHireRequest.getPlantHref()));
		if (!isNull(plantHireRequest.getApprovingWorksEngineer())) {
			plantHireRequestDTO.setApprovingWorksEngineer(employeeAssembler.toResource(plantHireRequest.getApprovingWorksEngineer()));
		}
		plantHireRequestDTO.setStatus(plantHireRequest.getStatus());
		plantHireRequestDTO.setSite(constructionSiteAssembler.toResource(plantHireRequest.getConstructionSite()));
		plantHireRequestDTO.setRequestingSiteEngineer(employeeAssembler.toResource(plantHireRequest.getRequestingSiteEngineer()));

		plantHireRequestDTO.setRentalCost(moneyAssembler.toResource(plantHireRequest.getRentalCost()));

		if (!isNull(plantHireRequest.getPurchaseOrder())) {
			plantHireRequestDTO.setPurchaseOrder(purchaseOrderAssembler.toResource(plantHireRequest.getPurchaseOrder()));
		}

		plantHireRequestDTO.setSupplier(supplierAssembler.toResource(plantHireRequest.getSupplier()));

		List<CommentDTO> commentDTOs = commentAssembler.toResources(plantHireRequest.getComments());
		plantHireRequestDTO.setComments(commentDTOs);

		return plantHireRequestDTO;
	}

}
