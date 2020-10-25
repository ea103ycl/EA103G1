package com.painter_tag.model;

public interface PainterTagDAO_interface {

	public Integer findTagNo(String tagDesc);
	public Integer insert(String tagDesc);
	public PainterTagVO getOneByTagNo(Integer tag_no);
	
}
