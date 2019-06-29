package com.esi.project.buildit.planthire.procurement.domain.repository;


import com.esi.project.buildit.planthire.common.domain.enums.Role;
import com.esi.project.buildit.planthire.common.domain.model.Employee;

import javax.persistence.EntityManager;
import java.util.Optional;

public class EmployeeRepoImpl implements CustomEmployeeRepo{

	private EntityManager entityManager;

	public EmployeeRepoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Optional<Employee> findOneEmployeeByRole(Role role) {
		return entityManager.createQuery("select emp from Employee emp.role = ?1",Optional.class)
				.setParameter("?1",role)
				.getSingleResult();
	}
}
