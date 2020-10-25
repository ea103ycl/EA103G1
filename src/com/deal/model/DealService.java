package com.deal.model;

import java.sql.Connection;
import java.util.List;

import com.detail.model.DetailVO;
import com.prod.model.ProdVO;
import com.sun.jmx.snmp.Timestamp;

public class DealService {
	
	private DealDAO_interface dao;

	public DealService() {
		dao = new DealDAO();
	}
	

	
	
	public DealVO getOneDeal(String mem_id) {
		return dao.findByPrimaryKey(mem_id);
	}
	
	
	public DealVO find_Memid_byPtr(Integer ptr_no) {
		return dao.find_Memid_byPtr(ptr_no);
	}
	

	
	
	
	
	
}


