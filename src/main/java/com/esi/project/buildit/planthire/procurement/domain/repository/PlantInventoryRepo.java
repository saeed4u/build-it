package com.esi.project.buildit.planthire.procurement.domain.repository;

import com.esi.project.buildit.planthire.procurement.domain.model.PlantInventoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantInventoryRepo extends JpaRepository<PlantInventoryEntry, String> {
}
