package com.msg.model;

import java.sql.Timestamp;

public class MsgVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String msg_no; // �T���y����
	private String sender; // �o�e���|���s��
	private String reciver; // �������|���s��
	private Integer msg_type; // �T������
	private String msg; // �T�����e
	private Timestamp send_dt; // �o�e�ɶ�

	public String getMsg_no() {
		return msg_no;
	}

	public void setMsg_no(String msg_no) {
		this.msg_no = msg_no;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public Integer getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(Integer msg_type) {
		this.msg_type = msg_type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Timestamp getSend_dt() {
		return send_dt;
	}

	public void setSend_dt(Timestamp send_dt) {
		this.send_dt = send_dt;
	}

}
