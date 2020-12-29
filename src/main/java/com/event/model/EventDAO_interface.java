package com.event.model;


import java.util.List;
//�D�D�v�ɷ|�ϥΪ���k
//��HashTag�⧹�ɵo���o�����D�D�v��

public interface EventDAO_interface {
	public String insert(EventVO eventVO);
	public void delete(String event_no);
	public void update(EventVO eventVO);
	public List<EventVO> findAllEvent();
	public EventVO findByPrimaryKey(String event_no);
	public List<EventVO> findWithoutEnd();//�����w����������
	public String findLastEndEvent();
	
}
