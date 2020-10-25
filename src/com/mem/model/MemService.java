package com.mem.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MemService {

	private MemDAO_interface dao;

	public MemService() {
		dao = new MemJDBCDAO();
	}

	public byte[] getMemberPhoto(String mem_id) {
		return dao.getMemberPhoto(mem_id);
	}
	
	
	public MemVO addMem(Integer lv, String m_accno, String m_psw, String m_name, String m_gender, Date m_bday,
			String m_phone, String m_mobile, Integer m_zip, String m_city, String m_addr, String m_email, String m_word,
			byte[] m_photo, Integer m_source, Integer m_active, Integer m_public, Integer m_bancount, Integer balance) {

		MemVO memVO = new MemVO();

		memVO.setLv(lv);
		memVO.setM_accno(m_accno);

		memVO.setM_psw(m_psw);
		memVO.setM_name(m_name);
		memVO.setM_gender(m_gender);

		memVO.setM_bday(m_bday);
		memVO.setM_phone(m_phone);
		memVO.setM_mobile(m_mobile);
		memVO.setM_zip(m_zip);
		memVO.setM_city(m_city);

		memVO.setM_addr(m_addr);
		memVO.setM_email(m_email);
		memVO.setM_word(m_word);
		memVO.setM_photo(m_photo);
		memVO.setM_source(m_source);

		memVO.setM_active(m_active);
		memVO.setM_public(m_public);
		memVO.setM_bancount(m_bancount);
		memVO.setBalance(balance);
		dao.insert(memVO);

		return memVO;

	}

	public MemVO updateByUser(String m_psw, String m_name, String m_gender, Date m_bday, String m_phone,
			String m_mobile, Integer m_zip, String m_city, String m_addr, String m_email, String m_word, byte[] m_photo,
			String mem_id) {

		MemVO memVO = new MemVO();

		memVO.setM_psw(m_psw);
		memVO.setM_name(m_name);
		memVO.setM_gender(m_gender);

		memVO.setM_bday(m_bday);
		memVO.setM_phone(m_phone);
		memVO.setM_mobile(m_mobile);
		memVO.setM_zip(m_zip);
		memVO.setM_city(m_city);

		memVO.setM_addr(m_addr);
		memVO.setM_email(m_email);
		memVO.setM_word(m_word);
		memVO.setM_photo(m_photo);
		memVO.setMem_id(mem_id);

		dao.updateByUser(memVO);

		return memVO;
	}

	public MemVO updateByStaff(Integer m_active, Integer m_public, Integer m_bancount, String mem_id) {

		MemVO memVO = new MemVO();

		memVO.setM_active(m_active);
		memVO.setM_public(m_public);
		memVO.setM_bancount(m_bancount);
		memVO.setMem_id(mem_id);

		dao.updateByStaff(memVO);
		return memVO;

	}

	public MemVO findByAccno(String m_accno) {

		return dao.findByAccno(m_accno);

	}

	public MemVO findByPrimaryKey(String mem_id) {

		return dao.findByPrimaryKey(mem_id);

	}

	public List<MemVO> findByPKNameAcc(String str) {

		return dao.findByPKNameAcc(str);

	}

	public Set<String> getAllUsrId() {

		return dao.getAllUsrId();

	}

	public Map<String, String> getAllAccounts() {

		return dao.getAllAccounts();

	}

	public List<MemVO> getAll() {
		System.out.println( "cnt=" + dao.getAll().size() );
		return dao.getAll();

	}

}

