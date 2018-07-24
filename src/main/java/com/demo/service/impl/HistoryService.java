package com.demo.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bean.HistoryBean;
import com.demo.dao.HistoryDaoInt;
import com.demo.model.Device;
import com.demo.model.Employee;
import com.demo.model.History;
import com.demo.service.HistoryServiceInt;


@Service("historyService")
@Transactional
public class HistoryService implements HistoryServiceInt {

	
	@Autowired
	private HistoryDaoInt historyDAO;
	private String retMessage = null;
	
	
	public String saveHistory(HistoryBean history) {
		retMessage =historyDAO.saveHistory(history);
		return retMessage;
		
	}
	
}
