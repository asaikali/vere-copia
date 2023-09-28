package com.example.printers;

import java.util.ArrayList;
import java.util.Collection;

import com.example.exchange.ProductStockLevelResponse;

public class ProductStockLevelPrinter extends AbstractRecordPrinter<ProductStockLevelResponse>
{
	protected static final String STORE_NUM_COL = "Store Number";
	protected static final String STORE_NAME_COL = "Store Name";
	protected static final String STORE_ADDR_COL = "Store Address";
	protected static final String SKU_COL = "SKU";
	protected static final String PROD_NAME_COL = "Product Name";
	protected static final String QUANTITY_COL = "Quantity";
	
	protected static final Collection<ReportColumn> REPORT_COLS;
	
	static
	{
		REPORT_COLS = new ArrayList<ReportColumn>();

		REPORT_COLS.add(new ReportColumn(STORE_NUM_COL, 13, "StoreNumber"));
		REPORT_COLS.add(new ReportColumn(STORE_NAME_COL, 15, "StoreName"));
		REPORT_COLS.add(new ReportColumn(STORE_ADDR_COL, 50, "StoreAddress"));		
		REPORT_COLS.add(new ReportColumn(SKU_COL, 8, "Sku"));
		REPORT_COLS.add(new ReportColumn(PROD_NAME_COL, 50, "Product"));
		REPORT_COLS.add(new ReportColumn(QUANTITY_COL, 10, "Quantity"));		
	}
	
	public ProductStockLevelPrinter()
	{
		super(146, REPORT_COLS);
	}
}
