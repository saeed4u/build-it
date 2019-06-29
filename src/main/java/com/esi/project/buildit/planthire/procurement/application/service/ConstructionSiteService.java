package com.esi.project.buildit.planthire.procurement.application.service;

import com.esi.project.buildit.planthire.common.application.exception.ResourceNotFoundException;
import com.esi.project.buildit.planthire.procurement.application.assembler.ConstructionSiteAssembler;
import com.esi.project.buildit.planthire.procurement.application.dto.ConstructionSiteDTO;
import com.esi.project.buildit.planthire.procurement.domain.model.ConstructionSite;
import com.esi.project.buildit.planthire.procurement.domain.repository.ConstructionSiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConstructionSiteService {

	private ConstructionSiteRepository repository;

	private ConstructionSiteAssembler constructionSiteAssembler;

	public ConstructionSiteService(ConstructionSiteRepository constructionSiteRepository, ConstructionSiteAssembler constructionSiteAssembler) {
		this.repository = constructionSiteRepository;
		this.constructionSiteAssembler = constructionSiteAssembler;
	}

	public ConstructionSite findConstructionSite(Long id){
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("No construction site with id %s was found",id)));
	}

	public ConstructionSiteDTO createConstructionSite(String address){
		ConstructionSite constructionSite = new ConstructionSite();
		constructionSite.setAddress(address);
		return constructionSiteAssembler.toResource(repository.save(constructionSite));
	}

	public List<ConstructionSiteDTO> getAllConstructionSites(){
		return repository.findAll().stream().map(constructionSite -> constructionSiteAssembler.toResource(constructionSite)).collect(Collectors.toList());
	}

}
