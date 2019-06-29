package com.esi.project.buildit.planthire.procurement.application.dto;

import com.esi.project.buildit.planthire.common.application.dto.BusinessPeriodDTO;
import com.esi.project.buildit.planthire.common.application.dto.EmployeeDTO;
import com.esi.project.buildit.planthire.common.application.dto.MoneyDTO;
import com.esi.project.buildit.planthire.common.domain.enums.PlantHireRequestStatus;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
public class PlantHireRequestDTO extends ResourceSupport {

	Long _id;

	ConstructionSiteDTO site;

	BusinessPeriodDTO rentalPeriod;

	SupplierDTO supplier;

	PlantInventoryEntryDTO plant;

	MoneyDTO rentalCost;

	List<CommentDTO> comments;


	PlantHireRequestStatus status;

	PurchaseOrderDTO purchaseOrder;

	EmployeeDTO requestingSiteEngineer;

	EmployeeDTO approvingWorksEngineer;

}
