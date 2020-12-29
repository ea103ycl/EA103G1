package com.preorderdetail.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.preorder.model.PreOrderVO;



public class PreOrderDetailService {
	private PreOrderDetailDAO_interface dao;
	
	public PreOrderDetailService() {
		dao = new PreOrderDetailDAO();
	}
	
	public List<PreOrderDetailVO> getAllByPo_no(String po_no) {
		System.out.println("Service - �ǳƶi�Jdao.getAllByPo_no(po_no);");
		return dao.findByPrimaryKey(po_no);
	}
	
	public List<PreOrderDetailVO> getAll(){
		System.out.println("�w�ʭq��Service - �i�JgetAll()��k");
		return dao.getAll();
	}
	
	public List<PreOrderDetailVO> getAll_OrderQty(){
		return dao.getAll_OrderQty();
	}
	
	public void deletePreOrderDetail(String po_prod_no) {
		dao.deleteBypo_prod_no(po_prod_no);
	}
//	public List<PreOrderVO> getAllMemReachDiscount(String po_prod_no){
//		System.out.println("�w�ʭq��Service - �i�JgetAll_OrderQty()��k");
//		List<PreOrderDetailVO> preDVOs=dao.
//		PreOrderDetailVO preOrderDetailVO=preDVOs.get(0);
//		preOrderDetailVO.getPo_no();
//		
//		List<PreOrderVO> preOVOs=new ArrayList<PreOrderVO>();
//		
//		
//		
//		return null;
//	}
}
