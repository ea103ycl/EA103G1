package com.preproduct.model;

import java.util.*;

import com.event_p.model.Event_PVO;
import com.material.model.Material_Data_VO;

public interface PreProductDAO_interface {
	public void insert(PreProductVO pre_productVO);
	public void insertByRanking(PreProductVO pre_productVO);
	public void update(PreProductVO pre_productVO);
	public void delete(String po_prod_no);
	public void switchPreProduct(PreProductVO pre_productVO);
	public PreProductVO findByPrimaryKey(String po_prod_no);
	public List<PreProductVO> getALL();
	public List<PreProductVO> GET_ALLOF_PREPRODUCT();
	//依活動編號查詢商品
	public Set<Event_PVO> getEventpnoByPreproduct(Integer event_p_no);
	//依素材編號查詢商品
	public Set<Material_Data_VO> getManoByPreproduct(String ma_no);
	
	
	
	
}
