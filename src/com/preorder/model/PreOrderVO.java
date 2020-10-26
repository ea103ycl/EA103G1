package com.preorder.model;
import java.sql.Timestamp;

public class PreOrderVO implements java.io.Serializable{
	private String po_no;
	private String mem_id;
	private Timestamp po_time;
	private Integer po_zip;
	private String po_name;
	private String po_phone;
	private String po_addr;
	private Integer po_status;
	private Integer po_total;
	private String po_note;
	
	public String getPo_no() {
		return po_no;
	}
	public void setPo_no(String po_no) {
		this.po_no = po_no;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public Timestamp getPo_time() {
		return po_time;
	}
	public void setPo_time(Timestamp po_time) {
		this.po_time = po_time;
	}
	public Integer getPo_zip() {
		return po_zip;
	}
	public void setPo_zip(Integer po_zip) {
		this.po_zip = po_zip;
	}
	public String getPo_name() {
		return po_name;
	}
	public void setPo_name(String po_name) {
		this.po_name = po_name;
	}
	public String getPo_phone() {
		return po_phone;
	}
	public void setPo_phone(String po_phone) {
		this.po_phone = po_phone;
	}
	public String getPo_addr() {
		return po_addr;
	}
	public void setPo_addr(String po_addr) {
		this.po_addr = po_addr;
	}
	public Integer getPo_status() {
		return po_status;
	}
	public void setPo_status(Integer po_status) {
		this.po_status = po_status;
	}
	public Integer getPo_total() {
		return po_total;
	}
	public void setPo_total(Integer po_total) {
		this.po_total = po_total;
	}
	public String getPo_note() {
		return po_note;
	}
	public void setPo_note(String po_note) {
		this.po_note = po_note;
	}
	
	
}
