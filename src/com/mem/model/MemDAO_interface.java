package com.mem.model;

import java.sql.Connection;
//List»Ý­n 
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MemDAO_interface {
	
	public byte[] getMemberPhoto(String mem_id);

	public void insert(MemVO memVO);

	public void updateByUser(MemVO memVO);

	public void updateByStaff(MemVO memVO);

	public MemVO findByPrimaryKey(String mem_id);

	public MemVO findByAccno(String m_accno);

	public List<MemVO> findByPKNameAcc(String str);

	public Set<String> getAllUsrId();

	public Map<String, String> getAllAccounts();

	public List<MemVO> getAll();

	public MemVO updateBalance(int amount, MemVO memVO, Connection con);

}
