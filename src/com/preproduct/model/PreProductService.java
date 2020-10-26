package com.preproduct.model;

import java.sql.Timestamp;
import java.util.List;

public class PreProductService {
	private PreProductDAO_interface dao;
	public PreProductService() {
		System.out.println("--------------------------------------");
		System.out.println("秈Service篶");
		dao = new PreProductJDBCDAO();
		System.out.println("ServiceㄏノPreProductJDBCDAODAO");
	}
	
	public PreProductVO addPreProduct(Integer event_p_no,String ma_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end,Integer po_price,String po_detail) {
		PreProductVO preproductVO = new PreProductVO();
		
		System.out.println("-------Service - 秈add非称set把计--------");
		preproductVO.setEvent_p_no(event_p_no);
		preproductVO.setMa_no(ma_no);
		preproductVO.setPo_start(po_start);
		preproductVO.setPo_end(po_end);
		preproductVO.setPo_price(po_price);
		preproductVO.setPo_detail(po_detail);
		
		dao.insert(preproductVO);
		System.out.println("Service - addЧΘset把计insert");
		
		return preproductVO;
	}
	
	public PreProductVO addByRanking(String ma_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end,Integer po_price,String po_detail) {
		PreProductVO preproductVO = new PreProductVO();
		
		System.out.println("-------Service - 秈addByRanking非称set把计--------");
		
		preproductVO.setMa_no(ma_no);
		preproductVO.setPo_start(po_start);
		preproductVO.setPo_end(po_end);
		preproductVO.setPo_price(po_price);
		preproductVO.setPo_detail(po_detail);
		
		dao.insertByRanking(preproductVO);
		System.out.println("Service - addByRankingЧΘset把计insertByRanking");
		
		return preproductVO;
	}
	
	public PreProductVO switchPreProduct(String po_prod_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end) {
		System.out.println("-------Service - 秈switchPreProduct--------");
		PreProductVO preproductVO = new PreProductVO();
		System.out.println("Service - 秈switch非称set把计");
		preproductVO.setPo_prod_no(po_prod_no);
		System.out.println("(po_prod_no)竒setVO.Po_prod_no");
		preproductVO.setPo_start(po_start);
		System.out.println("(po_start)竒setVO.po_start");
		preproductVO.setPo_end(po_end);
		System.out.println("(po_end)竒setVO.po_end");
		dao.switchPreProduct(preproductVO);
		System.out.println("Service - switchЧΘset把计");	
		System.out.println("Service - 肚ServiceswitchPreProduct");	
		return preproductVO;
	}
	
	
	public PreProductVO updatePreProduct(String po_prod_no,Integer event_p_no,String ma_no,java.sql.Timestamp po_start,
			java.sql.Timestamp po_end,Integer po_price,String po_detail) {
		PreProductVO preproductVO = new PreProductVO();
		
		System.out.println("Service - 秈update非称set把计");
		preproductVO.setPo_prod_no(po_prod_no);
		preproductVO.setEvent_p_no(event_p_no);
		preproductVO.setMa_no(ma_no);
		preproductVO.setPo_start(po_start);
		preproductVO.setPo_end(po_end);
		preproductVO.setPo_price(po_price);
		preproductVO.setPo_detail(po_detail);
		dao.update(preproductVO);
		System.out.println("Service - updateЧΘset把计update");		
		
		return preproductVO;
	}
	
	public void deletePreProduct(String po_prod_no) {
		dao.delete(po_prod_no);
	}
	
	public PreProductVO getOnePreProduct(String po_prod_no) {
		return dao.findByPrimaryKey(po_prod_no);
	}
	
	
	public List<PreProductVO> getAll(){
		System.out.println("箇潦坝珇Service - 秈getAll()よ猭");
		return dao.getALL();
	}
	
	public List<PreProductVO> GET_ALLOF_PREPRODUCT(){
		System.out.println("箇潦坝珇Service - 秈GET_ALLOF_PREPRODUCT()よ猭");
		return dao.GET_ALLOF_PREPRODUCT();
	}
	
	
}
