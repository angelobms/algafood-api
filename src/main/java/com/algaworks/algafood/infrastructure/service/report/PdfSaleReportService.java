package com.algaworks.algafood.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.service.OrderSaleQueryService;
import com.algaworks.algafood.domain.service.SaleReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfSaleReportService implements SaleReportService {

	@Autowired
	private OrderSaleQueryService orderSaleQueryService;

	@Override
	public byte[] emitDailySales(DailySaleFilter filter, String timeOffset) {
		try {
			var inputStream = this.getClass().getResourceAsStream("/reports/daily-sales.jasper");

			var params = new HashMap<String, Object>();
			params.put("REPORT_LOCALE", new Locale("pt", "BR"));

			var dailySales = orderSaleQueryService.findDailySales(filter, timeOffset);
			var dataSource = new JRBeanCollectionDataSource(dailySales);

			var jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);

			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("It was not possible to issue a daily sales report", e);
		}
	}

}
