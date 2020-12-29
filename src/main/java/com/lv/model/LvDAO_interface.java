package com.lv.model;

import java.util.List;

public interface LvDAO_interface {

	public void insert(LvVO lvVO);

	public void update(LvVO lvVO);

	public void delete(Integer lv);
	
	public LvVO findByPrimaryKey(Integer lv);
	
	public LvVO findByThresholdSetting(Integer f_cnt); //�̿�J���l�ܼƪ��e�d�߬O�_�w���ۦP��Ʀs�b

	public LvVO findLvByFansCnt(Integer f_cnt); // �̳Q�l�ܼƬd�ߩ��ݵ���

	public byte[] findIconByLv(Integer lv);
	
	public byte[] getOneMemberLvIcon(String mem_id);
	
	public List<LvVO> getAll();

}
