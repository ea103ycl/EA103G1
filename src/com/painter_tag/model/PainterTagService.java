package com.painter_tag.model;

public class PainterTagService {

	PainterTagDAO_interface dao;
	
	public PainterTagService() {
		dao = new PainterTagDAO();
	}

	public Integer insert(String tagDesc) {
		return dao.insert(tagDesc);
	}
	
	public PainterTagVO getOneByTagNo(Integer tag_no) {
		return dao.getOneByTagNo(tag_no);
	}
	
}
