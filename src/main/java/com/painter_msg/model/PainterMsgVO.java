package com.painter_msg.model;

import java.sql.Timestamp;

public class PainterMsgVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer msg_no; // �d���y����
	private Integer ptr_no; // �@�~�y����
	private String mem_id; // �d�����|���s��
	private String msg; // �d�����e
	private Timestamp msg_dt; // �d���ɶ�
	private Integer msg_stat; // �d�����A 1:���` 2:�ѳЧ@�̧R�� 3:�ѯd���̧R��

	public Integer getMsg_no() {
		return msg_no;
	}

	public void setMsg_no(Integer msg_no) {
		this.msg_no = msg_no;
	}

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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Timestamp getMsg_dt() {
		return msg_dt;
	}

	public void setMsg_dt(Timestamp msg_dt) {
		this.msg_dt = msg_dt;
	}

	public Integer getMsg_stat() {
		return msg_stat;
	}

	public void setMsg_stat(Integer msg_stat) {
		this.msg_stat = msg_stat;
	}

}
