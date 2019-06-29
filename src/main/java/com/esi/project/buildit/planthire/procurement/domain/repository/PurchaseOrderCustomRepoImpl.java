package com.esi.project.buildit.planthire.procurement.domain.repository;

import com.esi.project.buildit.planthire.common.domain.enums.RentItPurchaseOrderStatus;
import com.esi.project.buildit.planthire.procurement.domain.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

public class PurchaseOrderCustomRepoImpl implements PurchaseOrderCustomRepo {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<PurchaseOrder> findAllByStatusNotIn(RentItPurchaseOrderStatus... statuses) {
		return entityManager.createQuery("select po from PurchaseOrder po WHERE po.status not in :statuses",PurchaseOrder.class)
				.setParameter("statuses", Arrays.asList(statuses))
				.getResultList();
	}

	private String transFormStatuses(RentItPurchaseOrderStatus... statuses) {
		StringBuilder statusesString = new StringBuilder();
		for (int i = 0; i < statuses.length; i++) {
			statusesString.append(statuses[i]);
			if (i < statuses.length - 1) {
				statusesString.append(",");
			}
		}
		return statusesString.toString();
	}
}
