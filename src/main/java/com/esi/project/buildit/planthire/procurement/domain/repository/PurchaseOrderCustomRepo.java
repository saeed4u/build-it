package com.esi.project.buildit.planthire.procurement.domain.repository;

import com.esi.project.buildit.planthire.common.domain.enums.RentItPurchaseOrderStatus;
import com.esi.project.buildit.planthire.procurement.domain.model.PurchaseOrder;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderCustomRepo {

	List<PurchaseOrder> findAllByStatusNotIn(RentItPurchaseOrderStatus... statuses);
}
