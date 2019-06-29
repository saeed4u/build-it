package com.esi.project.buildit.planthire.invoice.domain.repository;


import com.esi.project.buildit.planthire.invoice.domain.model.Invoice;
import com.esi.project.buildit.planthire.invoice.domain.model.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	Invoice findByPurchaseOrderHref(String href);

	List<Invoice> findByDueDate(LocalDate dueDate);

	List<Invoice> findByStatus(InvoiceStatus status);
}
