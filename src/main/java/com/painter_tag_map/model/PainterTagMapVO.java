package com.painter_tag_map.model;

public class PainterTagMapVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer ptr_no; // �@�~�y����
	private Integer tag_no; // Hashtag�y����
	private Integer tag_seq; // HASHTAG��ܶ���

	public Integer getPtr_no() {
		return ptr_no;
	}

	public void setPtr_no(Integer ptr_no) {
		this.ptr_no = ptr_no;
	}

	public Integer getTag_no() {
		return tag_no;
	}

	public void setTag_no(Integer tag_no) {
		this.tag_no = tag_no;
	}

	public Integer getTag_seq() {
		return tag_seq;
	}

	public void setTag_seq(Integer tag_seq) {
		this.tag_seq = tag_seq;
	}

}
