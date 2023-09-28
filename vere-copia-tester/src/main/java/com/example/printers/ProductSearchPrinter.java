package com.example.printers;

import com.example.exchange.ProductSearchResponse;
import java.util.ArrayList;
import java.util.Collection;

public class ProductSearchPrinter extends AbstractRecordPrinter<ProductSearchResponse>
{
	protected static final String SKU_COL = "SKU";
	protected static final String DESC_COL = "Description";
	protected static final String QUANTITY_COL = "Quanity";
	
	protected static final Collection<ReportColumn> REPORT_COLS;
	
	static
	{
		REPORT_COLS = new ArrayList<ReportColumn>();

		REPORT_COLS.add(new ReportColumn(SKU_COL, 8, "Sku"));
		REPORT_COLS.add(new ReportColumn(DESC_COL, 50, "Description"));
		REPORT_COLS.add(new ReportColumn(QUANTITY_COL, 10, "Quantity"));		
			
	}
	
	public ProductSearchPrinter()
	{
		super(68, REPORT_COLS);
	}
}
