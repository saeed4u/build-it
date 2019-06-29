package com.esi.project.buildit.planthire.procurement.application.service;

import com.esi.project.buildit.planthire.common.application.dto.BusinessPeriodDTO;
import com.esi.project.buildit.planthire.common.rest.Response;
import com.esi.project.buildit.planthire.invoice.application.dto.InvoiceDto;
import com.esi.project.buildit.planthire.procurement.application.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Future;

@Service
@PropertySource("classpath:application.properties")
public class RentItIntegrationService {

	@Value("${rentItBaseUrl}")
	String rentItBaseUrl;

	@Value("${createPOUrl}")
	private String rentItCreatePOUrl;

	@Value("${catalogUrl}")
	private String rentItCatalogUrl;

	@Value("${paid-invoice-url}")
	private String invoicePaidUrl;

	private Logger logger = LoggerFactory.getLogger(RentItIntegrationService.class);

	@Async
	public Future<RentItPurchaseOrderDTO> createRentItPurchaseOrder(PlantInventoryEntryDTO plant, BusinessPeriodDTO businessPeriod, String address, BigDecimal total) {
		RestTemplate restTemplate = new RestTemplate();
		logger.info("URL = {}", rentItBaseUrl + rentItCreatePOUrl);
		HttpHeaders httpHeaders = getHttpHeaders();
		HttpEntity<RentItPORequestDTO> requestBodyAndHeaders = new HttpEntity<>(RentItPORequestDTO.of(plant, businessPeriod, address, total), httpHeaders);
		ResponseEntity<RentItPurchaseOrderDTO> response = restTemplate.exchange(rentItBaseUrl + rentItCreatePOUrl,
				HttpMethod.POST, requestBodyAndHeaders, RentItPurchaseOrderDTO.class);
		return new AsyncResult<>(response.getBody());
	}

	@Async
	public Future<RentItPurchaseOrderDTO> cancelRentItPurchaseOrder(Long poId) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = getHttpHeaders();
		HttpEntity<RentItPORequestDTO> requestBodyAndHeaders = new HttpEntity<>(httpHeaders);
		String url = rentItBaseUrl + "sales/orders/" + poId + "/cancel";
		logger.info("URL = {}", url);
		ResponseEntity<RentItPurchaseOrderDTO> response = restTemplate.exchange(url,
				HttpMethod.PUT, requestBodyAndHeaders, RentItPurchaseOrderDTO.class);
		return new AsyncResult<>(response.getBody());
	}

	@Async
	public void sendPaymentNotice(InvoiceDto invoiceDto) {
		RestTemplate restTemplate = new RestTemplate();
		logger.info("URL = {}", rentItBaseUrl + invoicePaidUrl);
		HttpHeaders httpHeaders = getHttpHeaders();
		HttpEntity<InvoiceDto> requestBodyAndHeaders = new HttpEntity<>(invoiceDto, httpHeaders);
		ResponseEntity<InvoiceDto> response = restTemplate.exchange(rentItBaseUrl + invoicePaidUrl,
				HttpMethod.PUT, requestBodyAndHeaders, InvoiceDto.class);

		logger.info("Response {}", response.getBody());
	}

	public List<RentItPurchaseOrderDTO> getPOStatuses(List<Long> ids) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = getHttpHeaders();
		HttpEntity<List<Long>> requestBodyAndHeaders = new HttpEntity<>(ids, httpHeaders);
		String url = rentItBaseUrl + "sales/orders/status";
		logger.info("URL = {}", url);
		ResponseEntity<List<RentItPurchaseOrderDTO>> response = restTemplate.exchange(url,
				HttpMethod.POST, requestBodyAndHeaders, new ParameterizedTypeReference<List<RentItPurchaseOrderDTO>>() {
				});
		return response.getBody();
	}

	@Async
	public Future<RentItPurchaseOrderDTO> requestExtension(Long id, POExtensionDTO poExtensionDTO) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = getHttpHeaders();
		HttpEntity<POExtensionDTO> requestBodyAndHeaders = new HttpEntity<>(poExtensionDTO, httpHeaders);
		String url = rentItBaseUrl + "sales/orders/" + id + "/extend";
		logger.info("URL = {}", url);
		ResponseEntity<RentItPurchaseOrderDTO> response = restTemplate.exchange(url,
				HttpMethod.PUT, requestBodyAndHeaders, RentItPurchaseOrderDTO.class);
		return new AsyncResult<>(response.getBody());
	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return httpHeaders;
	}

	RentItPlantInventoryEntryDTO fetchPlantEntry(String href) {
		logger.info("Url {}", href);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = getHttpHeaders();
		HttpEntity<Response<RentItPlantInventoryEntryDTO>> request = new HttpEntity<>(httpHeaders);
		RentItPlantInventoryEntryDTO dto = restTemplate.exchange(href, HttpMethod.GET, request
				, new ParameterizedTypeReference<Response<RentItPlantInventoryEntryDTO>>() {
				}).getBody().getData();
		logger.info("RentItPlantInventoryEntryDTO{}", dto);
		return dto;
	}

	Response<List<RentItPlantInventoryEntryDTO>> queryCatalog(String name, BusinessPeriodDTO businessPeriod) {
		HttpHeaders httpHeaders = getHttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		String url = UriComponentsBuilder.fromHttpUrl(rentItBaseUrl + rentItCatalogUrl)
				.queryParam("name", name)
				.queryParam("startDate", businessPeriod.getStartDate())
				.queryParam("endDate", businessPeriod.getEndDate()).toUriString();

		logger.info("URL {}", url);

		ResponseEntity<Response<List<RentItPlantInventoryEntryDTO>>> responseEntity = restTemplate.exchange(url, HttpMethod.GET
				, new HttpEntity<Response<List<RentItPlantInventoryEntryDTO>>>(httpHeaders)
				, new ParameterizedTypeReference<Response<List<RentItPlantInventoryEntryDTO>>>() {
				});
		logger.info("Response = {}", responseEntity.getBody().getData());
		return responseEntity.getBody();

	}


}
