package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;
import com.algaworks.algafood.domain.service.OrderSaleQueryService;
import com.algaworks.algafood.domain.service.SaleReportService;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {
	
	@Autowired
	private OrderSaleQueryService orderSaleQueryService;
	
	@Autowired
	private SaleReportService saleReportService; 
	
	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DailySale> consultDailySales(DailySaleFilter filter, 
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return orderSaleQueryService.findDailySales(filter, timeOffset);
	}
	
	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultDailySalesPdf(DailySaleFilter filter, 
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		byte[] bytesPdf = saleReportService.emitDailySales(filter, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");
		
		return ResponseEntity.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(bytesPdf);
	}

}
