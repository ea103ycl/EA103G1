package com.painter_act.model;

public class PainterActVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer ptr_no; // �@�~�y����
	private String mem_id; // ���ʪ��|���s��
	private Integer act_type; // ���ʤ覡 1:���w 2:����

	public Integer getPtr_no() {
		return ptr_no;
	}

	public void setPtr_no(Integer ptr_no) {
		this.ptr_no = ptr_no;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public Integer getAct_type() {
		return act_type;
	}

	public void setAct_type(Integer act_type) {
		this.act_type = act_type;
	}

}
