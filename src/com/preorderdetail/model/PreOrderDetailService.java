package com.preorderdetail.model;

import java.util.List;



public class PreOrderDetailService {
	private PreOrderDetailDAO_interface dao;
	
	public PreOrderDetailService() {
		dao = new PreOrderDetailDAOJDBC();
	}
	
	public List<PreOrderDetailVO> getAllByPo_no(String po_no) {
		System.out.println("Service - 準備進入dao.getAllByPo_no(po_no);");
		return dao.findByPrimaryKey(po_no);
	}
	
	public List<PreOrderDetailVO> getAll(){
		System.out.println("預購訂單Service - 進入getAll()方法");
		return dao.getAll();
	}
}
