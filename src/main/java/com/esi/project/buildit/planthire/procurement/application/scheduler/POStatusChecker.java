package com.esi.project.buildit.planthire.procurement.application.scheduler;

import com.esi.project.buildit.planthire.common.domain.enums.POExtensionStatus;
import com.esi.project.buildit.planthire.common.domain.enums.RentItPurchaseOrderStatus;
import com.esi.project.buildit.planthire.common.domain.model.BusinessPeriod;
import com.esi.project.buildit.planthire.common.domain.model.Money;
import com.esi.project.buildit.planthire.procurement.application.dto.PlantInventoryEntryDTO;
import com.esi.project.buildit.planthire.procurement.application.dto.RentItPurchaseOrderDTO;
import com.esi.project.buildit.planthire.procurement.application.service.PlantInventoryEntryService;
import com.esi.project.buildit.planthire.procurement.application.service.RentItIntegrationService;
import com.esi.project.buildit.planthire.procurement.domain.model.POExtension;
import com.esi.project.buildit.planthire.procurement.domain.model.PlantHireRequest;
import com.esi.project.buildit.planthire.procurement.domain.model.PurchaseOrder;
import com.esi.project.buildit.planthire.procurement.domain.repository.POExtensionRepo;
import com.esi.project.buildit.planthire.procurement.domain.repository.PlantHireRequestRepository;
import com.esi.project.buildit.planthire.procurement.domain.repository.PurchaseOrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.esi.project.buildit.planthire.common.domain.enums.RentItPurchaseOrderStatus.*;

@Component
public class POStatusChecker {

	@Autowired
	private PurchaseOrderRepo repo;

	@Autowired
	private RentItIntegrationService integrationService;

	@Autowired
	PlantHireRequestRepository plantHireRequestRepository;

	@Autowired
	POExtensionRepo poExtensionRepo;

	@Autowired
	PlantInventoryEntryService plantInventoryEntryService;


	private Money calculateRentalCost(String plantHref, BusinessPeriod rentalPeriod) {
		PlantInventoryEntryDTO plantDTO = plantInventoryEntryService.findPlantWithHref(plantHref);
		return Money.of(plantDTO.getPricePerDay().getTotal().multiply(BigDecimal.valueOf(Period.between(rentalPeriod.getStartDate(), rentalPeriod.getEndDate()).getDays() + 1)));
	}


	private Logger logger = LoggerFactory.getLogger(POStatusChecker.class);


	@Scheduled(fixedDelay = 1000 * 10)
	public void checkForAllPOUpdates() {
		logger.info("Checking for PO updates");
		List<Long> purchaseOrders = repo.findAllByStatusNotIn(new RentItPurchaseOrderStatus[]{CANCELLED, REJECTED, PLANT_RETURNED, INVOICED})
				.stream().map((purchaseOrder -> {
					String[] urlSegments = purchaseOrder.getHref().split("/");
					return Long.parseLong(urlSegments[urlSegments.length - 1]);
				})).collect(Collectors.toList());


		logger.info("IDs = {}", purchaseOrders);

		if (!purchaseOrders.isEmpty()) {

			List<RentItPurchaseOrderDTO> purchaseOrderDTOS = integrationService.getPOStatuses(purchaseOrders);

			logger.info("POS = {}", purchaseOrderDTOS);

			purchaseOrderDTOS.forEach(purchaseOrderDTO -> {
				if (purchaseOrderDTO.getLink() != null) {
					String href = purchaseOrderDTO.getLink().getHref();
					logger.info("Href {}", href);
					Optional<PurchaseOrder> optionalPO = repo.findById(href);
					if (!optionalPO.isPresent()) {
						logger.info("PO {} not found", purchaseOrderDTO);
					} else {
						PurchaseOrder purchaseOrder = optionalPO.get();
						RentItPurchaseOrderStatus status = purchaseOrder.getStatus();
						if (status == EXTENSION_ACCEPTED) {
							POExtension poExtension = purchaseOrder.getExtensions().stream().filter(extension -> extension.getStatus() == POExtensionStatus.PENDING).findFirst().orElse(null);
							if (poExtension != null) {
								logger.info("Extension accepted {}", poExtension);
								poExtension.setStatus(POExtensionStatus.ACCEPTED);
								PlantHireRequest plantHireRequest = plantHireRequestRepository.findByPurchaseOrder(purchaseOrder).orElse(null);

								logger.info("Plant hire {}", plantHireRequest);

								if (plantHireRequest != null) {

									BusinessPeriod businessPeriod = plantHireRequest.getRentalPeriod();
									BusinessPeriod newRentalPeriod = BusinessPeriod.of(businessPeriod.getStartDate(), poExtension.getEndDate());
									plantHireRequest.setRentalPeriod(newRentalPeriod);
									plantHireRequest.setRentalCost(calculateRentalCost(plantHireRequest.getPlantHref().getHref(), newRentalPeriod));
									poExtensionRepo.save(poExtension);
									plantHireRequestRepository.save(plantHireRequest);
								}
							}
						}
						purchaseOrder.setStatus(purchaseOrderDTO.getStatus());
						logger.info("PO {} updated", purchaseOrder);
						repo.save(purchaseOrder);
					}
				}
			});
		}

	}

}
