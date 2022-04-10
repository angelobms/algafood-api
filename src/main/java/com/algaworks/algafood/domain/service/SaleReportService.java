package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.DailySaleFilter;

public interface SaleReportService {
	
	byte[] emitDailySales(DailySaleFilter filter, String timeOffset);

}
