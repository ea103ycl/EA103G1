package com.follow.model;

public class FollowVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String mem_id; // �|���s��
	private String f_mem_id; // �l�ܪ��|���s��

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getF_mem_id() {
		return f_mem_id;
	}

	public void setF_mem_id(String f_mem_id) {
		this.f_mem_id = f_mem_id;
	}

}
