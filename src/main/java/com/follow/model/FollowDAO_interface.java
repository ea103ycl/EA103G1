package com.follow.model;

import java.util.List;

public interface FollowDAO_interface {
	
	public void update(FollowVO followVO);
	public Integer getCnt(String mem_id, String f_mem_id); //���o�l�ܸ��(�D�n�P�_�O�_�w�l��)
	public FollowVO getOne(String mem_id, String f_mem_id); //���o�l�ܸ��
	public List<FollowVO> getFollowList(String mem_id); //�ӷ|�����l�ܹ�H�C��
	public List<FollowVO> getFansList(String mem_id); //�l�ܸӷ|�����C��
	public Integer getFollowCnt(String mem_id); //�ӷ|�����l�ܹ�H�H��
	public Integer getFansCnt(String mem_id); //�ӷ|�����l�ܹ�H�H��
	
}
