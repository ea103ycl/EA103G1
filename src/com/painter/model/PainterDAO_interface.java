package com.painter.model;

import java.util.List;

public interface PainterDAO_interface {
	
	public void insert(PainterVO painterVO, String tag_desc);
	public void update(PainterVO painterVO, String tag_desc);
	public void delete(Integer ptr_no, Integer ptr_status);
	public List<PainterVO> getSomeoneAll(String mem_id, String login_mem_id);
	public List<PainterVO> getSomeoneCollection(String mem_id, String login_mem_id);
	public List<PainterVO> getSomeoneFollowAll(String login_mem_id);
	
	public byte[] getPicByPtrNo(Integer ptr_no);
	public PainterVO getOnePainter(Integer ptr_no);
	
	
	//=========================================
	
	public List<PainterVO> searchNewPics();
	public List<PainterVO> searchLikePics();
	public List<PainterVO> searchPopularPics();
	public List<PainterVO> searchPopularPics2();
	
}
