package com.lv.model;

import java.util.List;

public class LvService {

	private LvDAO_interface dao;

	/*�@�w�n�g�o�q......�_�h�|�X500 �L�kcall��DAO*/
	public LvService() {
		dao = new LvDAO();
	}

	public void insert(LvVO lvVO) {
		dao.insert(lvVO);
	}

	public void update(LvVO lvVO) {
		dao.update(lvVO);
	}

	public void delete(Integer lv) {
		dao.delete(lv);
	}

	public LvVO findByPrimaryKey(Integer lv) {
		return dao.findByPrimaryKey(lv);
	}
	
	public LvVO findByThresholdSetting(Integer f_cnt) {
		return dao.findByThresholdSetting(f_cnt);
	}

	public LvVO getLvByFansCnt(Integer f_cnt) {
		return dao.findLvByFansCnt(f_cnt);
	}

	public List<LvVO> getAll() {
		return dao.getAll();
	}

	public byte[] getIconByLv(Integer lv) {
		return dao.findIconByLv(lv);
	}
	
	//���o�Y��|�������{�O����icon
	public byte[] getOneMemberLvIcon(String mem_id) {
		return dao.getOneMemberLvIcon(mem_id);
	}
}
