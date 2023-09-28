package com.example.printers;

import java.util.Collection;

public interface RecordPrinter<T> 
{
	public String generateRecordReport(T rec);
	
	public String generateRecordsReport(Collection<T> recs);
	
	public void printRecord(T rec);
	
	public void printRecords(Collection<T> recs);
}