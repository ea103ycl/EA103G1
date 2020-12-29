package com.event_p_rep.model;

import java.util.List;
import java.util.Map;

public interface Event_P_RepDAO_interface {
	public void insert(Event_P_RepVO event_p_repVO);
	public void update(Integer event_p_no,Integer rep_stat);//�ק窱�A
	public void delete(Integer event_p_no,String mem_id);//�e�ݷ|���������|�C
	public List<Event_P_RepVO> findAllRep();
	public List<Event_P_RepVO> findRepByEvent_p_no(Integer event_p_no);
	public List<Event_P_RepVO> findRepByEventNo(String event_no);
	public boolean checkMemReport(String mem_id,String event_no);
	public List<Event_P_RepVO> findAllByRepStat(Integer rep_stat);
	public List<Event_P_RepVO> findAllByCompositeQuery(Map<String,String[]> map);
	public Event_P_RepVO findByMemId(String mem_id);//�i��S�Ψ�
	public Event_P_RepVO findReportByMemAndEventNo(String mem_id,String event_no);
//	public int getRepStatTwoChangeNumByEventNo(String event_no);
}
