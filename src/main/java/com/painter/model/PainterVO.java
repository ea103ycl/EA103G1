package com.painter.model;

import java.sql.Timestamp;

public class PainterVO {

	private static final long serialVersionUID = 1L;

	private Integer ptr_no; // �@�~�y����
	private String mem_id; // �|���s��
	private String ptr_nm; // �@�~�W��
	private String intro; // �@�~����
	private byte[] pic; // �@�~�Ϥ�
	private Integer priv_stat; // ���p�v���A 1:���} 2:�ȭ��ӤH�[�� 3:�ȭ������[��
	private Integer ptr_stat; // �@�~���A  1:���` 2:�ѳЧ@�̧R�� 3:�Ѻ޲z���R��
	private Integer like_cnt; // �Q���w����
	private Integer col_cnt; // �Q���æ���
	private Timestamp create_dt; // �@�~�إ߮ɶ�

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

	public String getPtr_nm() {
		return ptr_nm;
	}

	public void setPtr_nm(String ptr_nm) {
		this.ptr_nm = ptr_nm;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public Integer getPriv_stat() {
		return priv_stat;
	}

	public void setPriv_stat(Integer priv_stat) {
		this.priv_stat = priv_stat;
	}

	public Integer getPtr_stat() {
		return ptr_stat;
	}

	public void setPtr_stat(Integer ptr_stat) {
		this.ptr_stat = ptr_stat;
	}

	public Integer getLike_cnt() {
		return like_cnt;
	}

	public void setLike_cnt(Integer like_cnt) {
		this.like_cnt = like_cnt;
	}

	public Integer getCol_cnt() {
		return col_cnt;
	}

	public void setCol_cnt(Integer col_cnt) {
		this.col_cnt = col_cnt;
	}

	public Timestamp getCreate_dt() {
		return create_dt;
	}

	public void setCreate_dt(Timestamp create_dt) {
		this.create_dt = create_dt;
	}

}
