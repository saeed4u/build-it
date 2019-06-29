package com.esi.project.buildit.planthire.procurement.domain.model;

import com.esi.project.buildit.planthire.common.domain.enums.RentItPurchaseOrderStatus;
import com.esi.project.buildit.planthire.invoice.domain.model.Invoice;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class PurchaseOrder {

	@Id
	@URL
	String href;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	RentItPurchaseOrderStatus status;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
	List<POExtension> extensions = new ArrayList<>();


	@OneToOne
	PlantHireRequest plantHireRequest;

	@OneToOne
	Invoice invoice;

}