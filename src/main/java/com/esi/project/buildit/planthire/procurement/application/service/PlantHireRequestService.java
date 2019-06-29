package com.esi.project.buildit.planthire.procurement.application.service;

import com.esi.project.buildit.planthire.common.application.dto.BusinessPeriodDTO;
import com.esi.project.buildit.planthire.common.application.exception.ResourceNotFoundException;
import com.esi.project.buildit.planthire.common.application.service.BusinessPeriodAssembler;
import com.esi.project.buildit.planthire.common.domain.enums.POExtensionStatus;
import com.esi.project.buildit.planthire.common.domain.enums.PlantHireRequestStatus;
import com.esi.project.buildit.planthire.common.domain.enums.RentItPurchaseOrderStatus;
import com.esi.project.buildit.planthire.common.domain.enums.Role;
import com.esi.project.buildit.planthire.common.domain.model.BusinessPeriod;
import com.esi.project.buildit.planthire.common.domain.model.CustomLink;
import com.esi.project.buildit.planthire.common.domain.model.Employee;
import com.esi.project.buildit.planthire.common.domain.model.Money;
import com.esi.project.buildit.planthire.procurement.application.assembler.PlantHireRequestAssembler;
import com.esi.project.buildit.planthire.procurement.application.assembler.PlantInventoryEntryAssembler;
import com.esi.project.buildit.planthire.procurement.application.dto.*;
import com.esi.project.buildit.planthire.procurement.domain.model.*;
import com.esi.project.buildit.planthire.procurement.domain.repository.POExtensionRepo;
import com.esi.project.buildit.planthire.procurement.domain.repository.PlantHireRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class PlantHireRequestService {

	@Autowired
	PlantHireRequestRepository plantHireRequestRepository;

	@Autowired
	PlantInventoryEntryService plantInventoryEntryService;

	@Autowired
	SupplierService supplierService;

	@Autowired
	ConstructionSiteService constructionSiteService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	PlantHireRequestAssembler plantHireRequestAssembler;

	@Autowired
	RentItIntegrationService rentItIntegrationService;

	@Autowired
	BusinessPeriodAssembler businessPeriodAssembler;

	@Autowired
	PurchaseOrderService purchaseOrderService;

	@Autowired
	PlantInventoryEntryAssembler plantInventoryEntryAssembler;

	@Autowired
	CommentService commentService;

	@Autowired
	POExtensionRepo poExtensionRepo;

	Logger logger = LoggerFactory.getLogger(PlantHireRequestService.class);

	PlantHireRequest findPlantHireRequest(Long id) {
		return plantHireRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("No plant hire request with id %s was found", id)));
	}

	public PlantHireRequestDTO getPlantHireRequest(Long id) {
		return plantHireRequestAssembler.toResource(findPlantHireRequest(id));
	}

	public List<PlantHireRequestDTO> getAllHireRequests() {
		return plantHireRequestRepository.findAll().stream().map(hireRequest -> plantHireRequestAssembler.toResource(hireRequest)).collect(Collectors.toList());
	}

	@Transactional
	public PlantHireRequestDTO createPlantHireRequest(CreatePlantHireRequestDTO createPlantHireRequestDTO) {
		Optional<PlantInventoryEntry> optionalPlantInventoryEntry = plantInventoryEntryService.getPlantWithHref(createPlantHireRequestDTO.getPlantHref());
		PlantInventoryEntry plantInventoryEntry = optionalPlantInventoryEntry.orElseGet(() -> plantInventoryEntryService.createPlant(createPlantHireRequestDTO.getPlantId(), createPlantHireRequestDTO.getPlantHref()));

		PlantHireRequest plantHireRequest = new PlantHireRequest();
		plantHireRequest.setStatus(PlantHireRequestStatus.PENDING);
		plantHireRequest.setPlantHref(plantInventoryEntry);
		plantHireRequest.setComments(new ArrayList<>());

		BusinessPeriod rentalPeriod = BusinessPeriod.of(createPlantHireRequestDTO.getRentalPeriod().getStartDate(), createPlantHireRequestDTO.getRentalPeriod().getEndDate());
		plantHireRequest.setRentalPeriod(rentalPeriod);
		plantHireRequest.setRentalCost(calculateRentalCost(plantHireRequest.getPlantHref().getHref(), rentalPeriod));
		Employee siteEngineer = employeeService.getEmployeeWithRole(Role.SITE_ENGINEER);
		plantHireRequest.setRequestingSiteEngineer(siteEngineer);

		Supplier supplier = supplierService.getSupplier(createPlantHireRequestDTO.getSupplierId());
		plantHireRequest.setSupplier(supplier);

		ConstructionSite constructionSite = constructionSiteService.findConstructionSite(createPlantHireRequestDTO.getConstructionSiteId());
		plantHireRequest.setConstructionSite(constructionSite);

		return plantHireRequestAssembler.toResource(plantHireRequestRepository.save(plantHireRequest));

	}

	private Money calculateRentalCost(String plantHref, BusinessPeriod rentalPeriod) {
		PlantInventoryEntryDTO plantDTO = plantInventoryEntryService.findPlantWithHref(plantHref);
		return Money.of(plantDTO.getPricePerDay().getTotal().multiply(BigDecimal.valueOf(Period.between(rentalPeriod.getStartDate(), rentalPeriod.getEndDate()).getDays() + 1)));
	}

	public PlantHireRequestDTO updatePlantHireRequest(Long id, CreatePlantHireRequestDTO createPlantHireRequestDTO) {
		PlantHireRequest hireRequest = findPlantHireRequest(id);

		if (!hireRequest.getConstructionSite().getId().equals(createPlantHireRequestDTO.getConstructionSiteId())) {
			hireRequest.setConstructionSite(constructionSiteService.findConstructionSite(createPlantHireRequestDTO.getConstructionSiteId()));
		}

		BusinessPeriod hireRequestBP = hireRequest.getRentalPeriod();
		BusinessPeriodDTO newHireRequestBP = createPlantHireRequestDTO.getRentalPeriod();

		if (!hireRequestBP.getStartDate().equals(newHireRequestBP.getStartDate()) || !hireRequestBP.getEndDate().equals(newHireRequestBP.getEndDate())) {
			BusinessPeriod rentalPeriod = BusinessPeriod.of(newHireRequestBP.getStartDate(), newHireRequestBP.getEndDate());
			hireRequest.setRentalPeriod(rentalPeriod);
			hireRequest.setRentalCost(calculateRentalCost(hireRequest.getPlantHref().getHref(), rentalPeriod));
		}

		return plantHireRequestAssembler.toResource(plantHireRequestRepository.save(hireRequest));
	}

	public void acceptHireRequest(Long id) throws ExecutionException, InterruptedException {
		PlantHireRequest plantHireRequest = findPlantHireRequest(id);
		logger.info("PlantHireRequest {}", plantHireRequest);
		Future<RentItPurchaseOrderDTO> purchaseOrderDTO = rentItIntegrationService.createRentItPurchaseOrder(plantInventoryEntryAssembler.toResource(plantHireRequest.getPlantHref()),
				businessPeriodAssembler.toResource(plantHireRequest.getRentalPeriod()), plantHireRequest.getConstructionSite().getAddress(), plantHireRequest.getRentalCost().getTotal());

		RentItPurchaseOrderDTO rentItPurchaseOrderDTO = purchaseOrderDTO.get();

		logger.info("Purchase Order {}", rentItPurchaseOrderDTO);

		RentItPurchaseOrderStatus status = rentItPurchaseOrderDTO.getStatus();
		CustomLink link = rentItPurchaseOrderDTO.get_links();
		String href = link.getSelf().getHref();

		PurchaseOrder purchaseOrder = purchaseOrderService.createPO(href, status);
		plantHireRequest.setPurchaseOrder(purchaseOrder);
		Employee approvingEmployee = employeeService.getEmployeeWithRole(Role.WORKS_ENGINEER);
		plantHireRequest.setApprovingWorksEngineer(approvingEmployee);
		plantHireRequest.setStatus(PlantHireRequestStatus.ACCEPTED);
		plantHireRequestRepository.save(plantHireRequest);
	}

	public void cancelHireRequest(Long id) throws ExecutionException, InterruptedException {
		PlantHireRequest plantHireRequest = findPlantHireRequest(id);
		logger.info("PlantHireRequest {}", plantHireRequest);
		if (plantHireRequest.getStatus() == PlantHireRequestStatus.ACCEPTED) {
			PurchaseOrder purchaseOrder = plantHireRequest.getPurchaseOrder();
			String href = purchaseOrder.getHref();
			Future<RentItPurchaseOrderDTO> purchaseOrderDTO = rentItIntegrationService.cancelRentItPurchaseOrder(getPOId(href));

			RentItPurchaseOrderDTO rentItPurchaseOrderDTO = purchaseOrderDTO.get();
			logger.info("Purchase Order {}", rentItPurchaseOrderDTO);
			purchaseOrderService.updatePurchaseOrder(href, RentItPurchaseOrderStatus.CANCELLED);
		}

		plantHireRequest.setStatus(PlantHireRequestStatus.CANCELLED);

		plantHireRequestRepository.save(plantHireRequest);
	}

	public void requestExtension(Long id, POExtensionDTO poExtensionDTO) throws ExecutionException, InterruptedException {
		PlantHireRequest plantHireRequest = findPlantHireRequest(id);
		logger.info("PlantHireRequest {}", plantHireRequest);
		PurchaseOrder purchaseOrder = plantHireRequest.getPurchaseOrder();
		List<POExtension> extensions = purchaseOrder.getExtensions().stream().filter(extension -> extension.getStatus() == POExtensionStatus.PENDING).collect(Collectors.toList());
		if (extensions.isEmpty()) {
			String href = purchaseOrder.getHref();
			Future<RentItPurchaseOrderDTO> purchaseOrderDTO = rentItIntegrationService.requestExtension(getPOId(href), poExtensionDTO);
			RentItPurchaseOrderDTO rentItPurchaseOrderDTO = purchaseOrderDTO.get();
			logger.info("Purchase Order {}", rentItPurchaseOrderDTO);
			POExtension poExtension = new POExtension(poExtensionDTO.getEndDate(), POExtensionStatus.PENDING);
			poExtension.setPurchaseOrder(purchaseOrder);
			poExtension = poExtensionRepo.save(poExtension);
			logger.info("PO Extension {}", poExtension);
			purchaseOrder.getExtensions().add(poExtension);
			purchaseOrder.setStatus(RentItPurchaseOrderStatus.EXTENSION_REQUESTED);
			purchaseOrderService.save(purchaseOrder);
		}
	}

	private Long getPOId(String href) {
		String[] urlSegments = href.split("/");
		return Long.parseLong(urlSegments[urlSegments.length - 1]);

	}

	@Transactional
	public PlantHireRequestDTO rejectHireRequest(Long id, CommentDTO reason) {
		PlantHireRequest request = findPlantHireRequest(id);
		request.setStatus(PlantHireRequestStatus.REJECTED);
		reason.setPlantHireID(id);
		commentService.createComment(reason, request);
		return plantHireRequestAssembler.toResource(plantHireRequestRepository.save(request));
	}


}
