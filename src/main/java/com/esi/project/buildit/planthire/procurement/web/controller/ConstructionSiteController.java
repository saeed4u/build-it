package com.esi.project.buildit.planthire.procurement.web.controller;

import com.esi.project.buildit.planthire.procurement.application.dto.ConstructionSiteDTO;
import com.esi.project.buildit.planthire.procurement.application.service.ConstructionSiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("api/construction-site")
public class ConstructionSiteController extends BaseController<ConstructionSiteDTO> {
	private ConstructionSiteService constructionSiteService;

	public ConstructionSiteController(ConstructionSiteService constructionSiteService) {
		this.constructionSiteService = constructionSiteService;
	}

	@GetMapping
	public List<ConstructionSiteDTO> getAllConstructionSites() {
		return constructionSiteService.getAllConstructionSites();
	}

	@PostMapping("/create")
	public ResponseEntity<ConstructionSiteDTO> createConstructionSite(@RequestBody ConstructionSiteDTO constructionSiteDTO) throws URISyntaxException {
		ConstructionSiteDTO newSite = constructionSiteService.createConstructionSite(constructionSiteDTO.getAddress());
		URI href = new URI(newSite.getId().getHref());
		return createdResponse(newSite, href);
	}
}
