package com.demo.dao;

import java.util.List;

import com.demo.bean.HistoryBean;
import com.demo.model.History;

public interface HistoryDaoInt {

	String saveHistory(HistoryBean history);
	List<History> getHistoryByPartNumber(String partNumber);
	List<History> getAllHistoryByPartNumber();
	List<History> getSiteStockHistoryByPartNumber(String partNumber);
	List<History> getAllSiteStockHistoryByPartNumber();
	List<History> getBootStockHistoryByPartNumber(String partNumber);
	List<History> getAllBootStockHistoryByPartNumber();
}
