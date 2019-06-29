package com.esi.project.buildit.planthire.procurement.domain.model;

import com.esi.project.buildit.planthire.common.domain.enums.PlantHireRequestStatus;
import com.esi.project.buildit.planthire.common.domain.model.BusinessPeriod;
import com.esi.project.buildit.planthire.common.domain.model.Employee;
import com.esi.project.buildit.planthire.common.domain.model.Money;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PlantHireRequest {
	@Id
	@GeneratedValue
	Long id;

	@Embedded
	@Column(nullable = false)
	BusinessPeriod rentalPeriod;

	@Embedded
	@Column(nullable = false)
	Money rentalCost;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "plantHireRequest", cascade = CascadeType.ALL)
	List<Comment> comments = new ArrayList<>();

	@JoinColumn(name = "purchase_order_id")
	@OneToOne
	PurchaseOrder purchaseOrder;

	@JoinColumn(name = "requesting_site_engineer_id", nullable = false)
	@ManyToOne(optional = false)
	Employee requestingSiteEngineer;

	@JoinColumn(name = "approving_works_engineer_id")
	@ManyToOne
	Employee approvingWorksEngineer;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	PlantHireRequestStatus status;

	@JoinColumn(name = "plant_href", nullable = false)
	@ManyToOne(optional = false)
	PlantInventoryEntry plantHref;

	@JoinColumn(name = "supplier_id", nullable = false)
	@ManyToOne(optional = false)
	Supplier supplier;

	@JoinColumn(name = "construction_site_id", nullable = false)
	@ManyToOne(optional = false)
	ConstructionSite constructionSite;



}
