package com.preproduct.model;

import java.util.List;

public class PreProductService {
	private PreProductDAO dao;
	public PreProductService() {
		System.out.println("--------------------------------------");
		System.out.println("�i�JService���غc�l");
		dao = new PreProductDAO();
		System.out.println("Service�ϥ�PreProductDAO");
		System.out.println("--------------------------------------");
	}
	
	public PreProductVO addPreProduct(Integer event_p_no,String ma_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end,Integer po_price,String po_detail) {
		System.out.println("-------Service - formĲ�oaddPreProduct--------");
		
		PreProductVO preproductVO = new PreProductVO();
		preproductVO.setEvent_p_no(event_p_no);
		preproductVO.setMa_no(ma_no);
		preproductVO.setPo_start(po_start);
		preproductVO.setPo_end(po_end);
		preproductVO.setPo_price(po_price);
		preproductVO.setPo_detail(po_detail);
		 
		System.out.println("Service - �ǳƨϥ�dao.insert");
		dao.insert(preproductVO);
		System.out.println("Service - dao.insert���槹���Areturn preproductVO");
		System.out.println("--------------------------------------");
		return preproductVO;
	}
	
	public PreProductVO addByRanking(String ma_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end,Integer po_price,String po_detail) {
		System.out.println("-------Service - formĲ�oaddByRanking--------");
		
		PreProductVO preproductVO = new PreProductVO();
		preproductVO.setMa_no(ma_no);
		preproductVO.setPo_start(po_start);
		preproductVO.setPo_end(po_end);
		preproductVO.setPo_price(po_price);
		preproductVO.setPo_detail(po_detail);
		
		System.out.println("Service - �ǳƨϥ�dao.insertByRanking");
		dao.insertByRanking(preproductVO);
		System.out.println("Service - dao.insertByRanking���槹���Areturn preproductVO");
		System.out.println("--------------------------------------");
		return preproductVO;
	}
	
	public PreProductVO getOnePreProduct(String po_prod_no) {
		return dao.findByPrimaryKey(po_prod_no);
	}
	
	public PreProductVO updatePreProduct(String po_prod_no,Integer event_p_no,String ma_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end,Integer po_price,String po_detail) {
		System.out.println("-------Service - formĲ�oupdatePreProduct--------");
		
		PreProductVO preproductVO = new PreProductVO();
		preproductVO.setPo_prod_no(po_prod_no);
		preproductVO.setEvent_p_no(event_p_no);
		preproductVO.setMa_no(ma_no);
		preproductVO.setPo_start(po_start);
		preproductVO.setPo_end(po_end);
		preproductVO.setPo_price(po_price);
		preproductVO.setPo_detail(po_detail);
		
		System.out.println("Service - �ǳƨϥ�dao.update");
		dao.update(preproductVO);
		System.out.println("Service - dao.update���槹���Areturn preproductVO");
		System.out.println("--------------------------------------");
		
		return preproductVO;
	}
	
	public PreProductVO switchPreProduct(String po_prod_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end) {
		System.out.println("-------Service - formĲ�oswitchPreProduct--------");
		
		PreProductVO preproductVO = new PreProductVO();
		preproductVO.setPo_prod_no(po_prod_no);
		preproductVO.setPo_start(po_start);
		preproductVO.setPo_end(po_end);
		
		
		System.out.println("Service - �ǳƨϥ�dao.switchPreProduct");
		dao.switchPreProduct(preproductVO);
		System.out.println("Service - dao.switchPreProduct���槹���Areturn preproductVO");
		System.out.println("--------------------------------------");
		return preproductVO;
	}

	public void deletePreProduct(String po_prod_no) {
		dao.delete(po_prod_no);
	}

	public List<PreProductVO> getALLPreproductInTime(){
		System.out.println("-------Service - form Ĳ�o getAll()--------");
		return dao.getALLPreproductInTime();
		
	}
	
	public List<PreProductVO> GET_ALLOF_PREPRODUCT(){
		System.out.println("-------Service - form Ĳ�o GET_ALLOF_PREPRODUCT()--------");
		return dao.GET_ALLOF_PREPRODUCT();
	}
	
	public List<PreProductVO> getAll(){
		System.out.println("-------Service - form Ĳ�o getAll()--------");
		return dao.getALL();
		
	}
	
}
