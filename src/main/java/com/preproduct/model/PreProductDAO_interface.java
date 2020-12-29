package com.preproduct.model;

import java.util.*;

import com.event_p.model.Event_PVO;
import com.material.model.Material_Data_VO;

public interface PreProductDAO_interface {
	public void insert(PreProductVO pre_productVO);
	//��@�������A��q���͹w�ʰӫ~
	public void insertByRanking(PreProductVO pre_productVO);
	public void update(PreProductVO pre_productVO);
	public void delete(String po_prod_no);
	//����"�w�ʶ}�l"and"�w�ʵ���"�A�ǥH�W/�U�[�ӫ~�C
	public void switchPreProduct(PreProductVO pre_productVO);
	public PreProductVO findByPrimaryKey(String po_prod_no);
	public List<PreProductVO> getALLPreproductInTime();
	//�ھ�"���ʽs��"���o"1��10�W"�B"���A��3"���@�~�Ҧ���T�C
	public List<PreProductVO> GET_ALLOF_PREPRODUCT();
	//�̬��ʽs���d�߰ӫ~
	public Set<Event_PVO> getEventpnoByPreproduct(Integer event_p_no);
	//�̯����s���d�߰ӫ~
	public Set<Material_Data_VO> getManoByPreproduct(String ma_no);
	List<PreProductVO> getALL();

	 
	
	
	
}
