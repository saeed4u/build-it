package com.esi.project.buildit.planthire.procurement.domain.repository;

import com.esi.project.buildit.planthire.procurement.domain.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepo extends JpaRepository<Supplier,Long> {
}
