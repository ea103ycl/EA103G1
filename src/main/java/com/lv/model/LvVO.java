package com.lv.model;

public class LvVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer lv; // ���{�O����
	private String lv_nm; // ���{�O�W��
	private byte[] icon; // ���{�O�ϥ�
	private Integer f_cnt; // ���{�O���e(�̯�����)

	public Integer getLv() {
		return lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	public String getLv_nm() {
		return lv_nm;
	}

	public void setLv_nm(String lv_nm) {
		this.lv_nm = lv_nm;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public Integer getF_cnt() {
		return f_cnt;
	}

	public void setF_cnt(Integer f_cnt) {
		this.f_cnt = f_cnt;
	}

}
