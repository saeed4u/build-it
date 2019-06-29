package com.esi.project.buildit.planthire.procurement.domain.repository;

import com.esi.project.buildit.planthire.procurement.domain.model.PlantHireRequest;
import com.esi.project.buildit.planthire.procurement.domain.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlantHireRequestRepository extends JpaRepository<PlantHireRequest,Long> {

	Optional<PlantHireRequest> findByPurchaseOrder(PurchaseOrder purchaseOrder);
}
