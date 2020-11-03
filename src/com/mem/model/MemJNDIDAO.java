package com.mem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import tools.DateTool;

public class MemJNDIDAO implements MemDAO_interface
{

//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	String userid = "G1";
//	String passwd = "123456";

	private static DataSource ds = null;
	static
		{
			try
				{
					Context ctx = new InitialContext();
					ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G1");

				} catch (NamingException e)
				{
					e.printStackTrace();
				}

		}

	private static final String INSERT_STMT = "INSERT INTO MEMBERS VALUES(('M'||LPAD(mem_seq.nextval, 6, '0')), ? , ?, ? , ?, ? , ?, ? , ? , ? , ?, ? , ? ,? ,? , ? ,CURRENT_TIMESTAMP, ? , ? , ? , ?)";

	private static final String UPDATE_PHOTO = "UPDATE MEMBERS SET m_photo =? WHERE mem_id = ?";

	private static final String UPDATE_BALANCE_BY_ID = "UPDATE MEMBERS SET balance = ? WHERE MEM_ID = ?";

	private static final String UPDATE_PSW_BY_ID = "UPDATE MEMBERS SET m_psw = ? WHERE MEM_ID = ?";

	private static final String UPDATE_BY_USER = "UPDATE MEMBERS SET m_name=?, m_gender=?,m_bday=?,m_phone=?,m_mobile=?,m_zip=?,m_city=?,m_addr=?,m_email=? WHERE MEM_ID = ?";

	private static final String UPDATE_BY_STAFF = "UPDATE MEMBERS SET m_active=?, m_public=? ,m_bancount= ? WHERE MEM_ID = ?";

	private static final String GET_ONE_STMT = "SELECT * FROM MEMBERS WHERE mem_id = ?";

	private static final String GET_ONE_BY_ACCNO = "SELECT * FROM MEMBERS WHERE m_accno = ? OR m_email=?";

	private static final String GET_USERID = "SELECT m_accno FROM MEMBERS";

	private static final String GETPASSWORD_BYACCNO = "SELECT m_accno,m_psw FROM MEMBERS";

	private static final String GET_ALL_BY_ID_NAME_ACC = "SELECT * FROM MEMBERS WHERE mem_id LIKE ? OR m_name LIKE ? OR m_accno LIKE ?";

	private static final String GET_ALL_STMT = "SELECT mem_id,lv,m_accno,m_psw,m_name,m_gender,m_bday,m_phone,m_mobile,m_zip,m_city,m_addr,m_email,m_word,"
			+ "m_photo,m_source,m_joindate,m_active,m_public,m_bancount,balance FROM MEMBERS ORDER BY MEM_ID";

	private static final String GET_ALL_DESC = "SELECT mem_id,lv,m_accno,m_psw,m_name,m_gender,m_bday,m_phone,m_mobile,m_zip,m_city,m_addr,m_email,m_word,"
			+ "m_photo,m_source,m_joindate,m_active,m_public,m_bancount,balance FROM MEMBERS ORDER BY MEM_ID DESC";

	private static final String GET_PHOTO = "SELECT m_photo FROM MEMBERS WHERE mem_id = ?";

	// 新增會員

	@Override
	public void insert(MemVO memVO)
	{

		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setInt(1, memVO.getLv());
				pstmt.setString(2, memVO.getM_accno());
				pstmt.setString(3, memVO.getM_psw());
				pstmt.setString(4, memVO.getM_name());
				pstmt.setString(5, memVO.getM_gender());

				pstmt.setDate(6, memVO.getM_bday());
				pstmt.setString(7, memVO.getM_phone());
				pstmt.setString(8, memVO.getM_mobile());
				pstmt.setInt(9, memVO.getM_zip());
				pstmt.setString(10, memVO.getM_city());

				pstmt.setString(11, memVO.getM_addr());
				pstmt.setString(12, memVO.getM_email());
				pstmt.setString(13, memVO.getM_word());
				pstmt.setBytes(14, memVO.getM_photo());
				pstmt.setInt(15, memVO.getM_source());

				pstmt.setInt(16, memVO.getM_active());
				pstmt.setInt(17, memVO.getM_public());
				pstmt.setInt(18, memVO.getM_bancount());
				pstmt.setInt(19, memVO.getBalance());

				pstmt.executeUpdate();

			} catch (SQLException e)
			{

				e.printStackTrace();
			}

		finally
			{

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (Exception e)
							{
								e.printStackTrace();
							}
					}

			}

	}

	// 新增大頭照
	@Override
	public void insertPhoto(String mem_id, byte[] photo)
	{

		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_PHOTO);

				pstmt.setBytes(1, photo);
				pstmt.setString(2, mem_id);

				pstmt.executeUpdate();

				// 清空裡面參數，重覆使用已取得的PreparedStatement物件
				pstmt.clearParameters();

				System.out.println("新增成功");

			} catch (SQLException e)
			{

				e.printStackTrace();
			} finally
			{
				// 依建立順序關閉資源 (越晚建立越早關閉)
				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException se)
							{
								System.out.println(se);
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException se)
							{
								System.out.println(se);
							}
					}
			}

	}

	// 更新餘額
	@Override
	public MemVO updateBalance(int amount, MemVO memVO, Connection con)
	{

		PreparedStatement pstmt = null;

		int oldBalance = memVO.getBalance();

		int newBalance = oldBalance + amount;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_BALANCE_BY_ID);

				pstmt.setInt(1, newBalance);
				pstmt.setString(2, memVO.getMem_id());

				pstmt.executeUpdate();

				memVO.setBalance(newBalance);

				System.out.println("會員id:" + memVO.getMem_id());
				System.out.println("欲加值金額:" + amount);
				System.out.println("加值後餘額:" + newBalance);

			} catch (SQLException e)
			{
				if (con != null)
					{
						try
							{
								// 設定於當有exception發生時之catch區塊內
								con.rollback();
								memVO.setBalance(oldBalance);
							} catch (SQLException excep)
							{
								throw new RuntimeException("rollback error occured. " + excep.getMessage());
							}
					}
				throw new RuntimeException("A database error occured. " + e.getMessage());

			} finally
			{

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (Exception e)
							{
								e.printStackTrace();
							}
					}

			}
		return memVO;

	}

	// 更新密碼
	@Override
	public void updatePsw(String mem_id, String m_psw)
	{

		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_PSW_BY_ID);

				pstmt.setString(1, m_psw);
				pstmt.setString(2, mem_id);

				pstmt.executeUpdate();

			} catch (SQLException e)
			{
				e.printStackTrace();

			} finally
			{

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (Exception e)
							{
								e.printStackTrace();
							}
					}

			}

	}

	// 前台修改

	@Override
	public void updateByUser(MemVO memVO)
	{

		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_BY_USER);

				pstmt.setString(1, memVO.getM_name());
				pstmt.setString(2, memVO.getM_gender());
				pstmt.setDate(3, memVO.getM_bday());
				pstmt.setString(4, memVO.getM_phone());
				pstmt.setString(5, memVO.getM_mobile());
				pstmt.setInt(6, memVO.getM_zip());
				pstmt.setString(7, memVO.getM_city());

				pstmt.setString(8, memVO.getM_addr());
				pstmt.setString(9, memVO.getM_email());

				pstmt.setString(10, memVO.getMem_id());

				pstmt.executeUpdate();

			} catch (SQLException e)
			{

				e.printStackTrace();
			} finally
			{

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (Exception e)
							{
								e.printStackTrace();
							}
					}

			}

	}

	// 後台修改

	@Override
	public void updateByStaff(MemVO memVO)
	{

		Connection con = null;
		PreparedStatement pstmt = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_BY_STAFF);

				pstmt.setInt(1, memVO.getM_active());
				pstmt.setInt(2, memVO.getM_public());
				pstmt.setInt(3, memVO.getM_bancount());
				pstmt.setString(4, memVO.getMem_id());

				pstmt.executeUpdate();

			} catch (SQLException e)
			{

				e.printStackTrace();
			} finally
			{

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (Exception e)
							{
								e.printStackTrace();
							}
					}

			}
	}

	// 查詢BY 會員帳號 或 會員信箱
	@Override
	public MemVO findByAccnoEmail(String input)
	{

		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_BY_ACCNO);

				pstmt.setString(1, input);
				pstmt.setString(2, input);

				rs = pstmt.executeQuery();

				while (rs.next())
					{
						memVO = new MemVO();
						memVO.setMem_id(rs.getString("mem_id"));
						memVO.setLv(rs.getInt("lv"));
						memVO.setM_accno(rs.getString("m_accno"));

						memVO.setM_psw(rs.getString("m_psw"));
						memVO.setM_name(rs.getString("m_name"));
						memVO.setM_gender(rs.getString("m_gender"));

						memVO.setM_bday(rs.getDate("m_bday"));
						memVO.setM_phone(rs.getString("m_phone"));
						memVO.setM_mobile(rs.getString("m_mobile"));
						memVO.setM_zip(rs.getInt("m_zip"));
						memVO.setM_city(rs.getString("m_city"));

						memVO.setM_addr(rs.getString("m_addr"));
						memVO.setM_email(rs.getString("m_email"));
						memVO.setM_word(rs.getString("m_word"));
						memVO.setM_photo(rs.getBytes("m_photo"));
						memVO.setM_source(rs.getInt("m_source"));
						memVO.setM_joindate(DateTool.timestamp2StringMin(rs.getTimestamp("m_joindate")));

						memVO.setM_active(rs.getInt("m_active"));
						memVO.setM_public(rs.getInt("m_public"));
						memVO.setM_bancount(rs.getInt("m_bancount"));
						memVO.setBalance(rs.getInt("balance"));

					}
			} catch (SQLException e)
			{
				e.printStackTrace();

			} finally
			{
				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}
			}
		return memVO;

	}

	// 查詢byPK
	@Override
	public MemVO findByPrimaryKey(String mem_id)
	{

		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, mem_id);
				rs = pstmt.executeQuery();

				while (rs.next())
					{
						memVO = new MemVO(); // ??????
						memVO.setMem_id(rs.getString("mem_id"));
						memVO.setLv(rs.getInt("lv"));
						memVO.setM_accno(rs.getString("m_accno"));

						memVO.setM_psw(rs.getString("m_psw"));
						memVO.setM_name(rs.getString("m_name"));
						memVO.setM_gender(rs.getString("m_gender"));

						memVO.setM_bday(rs.getDate("m_bday"));
						memVO.setM_phone(rs.getString("m_phone"));
						memVO.setM_mobile(rs.getString("m_mobile"));
						memVO.setM_zip(rs.getInt("m_zip"));
						memVO.setM_city(rs.getString("m_city"));

						memVO.setM_addr(rs.getString("m_addr"));
						memVO.setM_email(rs.getString("m_email"));
						memVO.setM_word(rs.getString("m_word"));
						memVO.setM_photo(rs.getBytes("m_photo"));
						memVO.setM_source(rs.getInt("m_source"));
						memVO.setM_joindate(DateTool.timestamp2StringMin(rs.getTimestamp("m_joindate")));

						memVO.setM_active(rs.getInt("m_active"));
						memVO.setM_public(rs.getInt("m_public"));
						memVO.setM_bancount(rs.getInt("m_bancount"));
						memVO.setBalance(rs.getInt("balance"));

					}
			} catch (SQLException e)
			{
				e.printStackTrace();

			} finally
			{
				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}
			}
		return memVO;

	}

	// 查詢byPK或會員名稱或會員帳號
	@Override
	public List<MemVO> findByPKNameAcc(String str)
	{

		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_BY_ID_NAME_ACC);
				pstmt.setString(1, "%" + str + "%");
				pstmt.setString(2, "%" + str + "%");
				pstmt.setString(3, "%" + str + "%");

				System.out.println(GET_ALL_BY_ID_NAME_ACC);

				rs = pstmt.executeQuery();

				while (rs.next()) // rs往下移一筆, 若有值則回傳ture
					{
						memVO = new MemVO();
						memVO.setMem_id(rs.getString("mem_id"));
						memVO.setLv(rs.getInt("lv"));
						memVO.setM_accno(rs.getString("m_accno"));

						memVO.setM_psw(rs.getString("m_psw"));
						memVO.setM_name(rs.getString("m_name"));
						memVO.setM_gender(rs.getString("m_gender"));

						memVO.setM_bday(rs.getDate("m_bday"));
						memVO.setM_phone(rs.getString("m_phone"));
						memVO.setM_mobile(rs.getString("m_mobile"));
						memVO.setM_zip(rs.getInt("m_zip"));
						memVO.setM_city(rs.getString("m_city"));

						memVO.setM_addr(rs.getString("m_addr"));
						memVO.setM_email(rs.getString("m_email"));
						memVO.setM_word(rs.getString("m_word"));
						memVO.setM_photo(rs.getBytes("m_photo"));
						memVO.setM_source(rs.getInt("m_source"));
						memVO.setM_joindate(DateTool.timestamp2StringMin(rs.getTimestamp("m_joindate")));

						memVO.setM_active(rs.getInt("m_active"));
						memVO.setM_public(rs.getInt("m_public"));
						memVO.setM_bancount(rs.getInt("m_bancount"));
						memVO.setBalance(rs.getInt("balance"));

						list.add(memVO);

					}

			} catch (SQLException e)
			{

				e.printStackTrace();
			} finally
			{
				if (rs != null)
					{
						try
							{
								rs.close();
							} catch (SQLException e)
							{
								e.printStackTrace();
							}
					}

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

			}

		return list;
	}

	// 查詢全部
	@Override
	public List<MemVO> getAll()
	{

		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) // rs往下移一筆, 若有值則回傳ture
					{
						memVO = new MemVO();
						memVO.setMem_id(rs.getString("mem_id"));
						memVO.setLv(rs.getInt("lv"));
						memVO.setM_accno(rs.getString("m_accno"));

						memVO.setM_psw(rs.getString("m_psw"));
						memVO.setM_name(rs.getString("m_name"));
						memVO.setM_gender(rs.getString("m_gender"));

						memVO.setM_bday(rs.getDate("m_bday"));
						memVO.setM_phone(rs.getString("m_phone"));
						memVO.setM_mobile(rs.getString("m_mobile"));
						memVO.setM_zip(rs.getInt("m_zip"));
						memVO.setM_city(rs.getString("m_city"));

						memVO.setM_addr(rs.getString("m_addr"));
						memVO.setM_email(rs.getString("m_email"));
						memVO.setM_word(rs.getString("m_word"));
						memVO.setM_photo(rs.getBytes("m_photo"));
						memVO.setM_source(rs.getInt("m_source"));
						memVO.setM_joindate(DateTool.timestamp2StringMin(rs.getTimestamp("m_joindate")));

						memVO.setM_active(rs.getInt("m_active"));
						memVO.setM_public(rs.getInt("m_public"));
						memVO.setM_bancount(rs.getInt("m_bancount"));
						memVO.setBalance(rs.getInt("balance"));

						list.add(memVO);

					}

			} catch (SQLException e)
			{

				e.printStackTrace();
			} finally
			{
				if (rs != null)
					{
						try
							{
								rs.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

			}

		return list;
	}

	@Override
	public List<MemVO> getAllDESC()
	{

		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_DESC);
				rs = pstmt.executeQuery();

				while (rs.next()) // rs往下移一筆, 若有值則回傳ture
					{
						memVO = new MemVO();
						memVO.setMem_id(rs.getString("mem_id"));
						memVO.setLv(rs.getInt("lv"));
						memVO.setM_accno(rs.getString("m_accno"));

						memVO.setM_psw(rs.getString("m_psw"));
						memVO.setM_name(rs.getString("m_name"));
						memVO.setM_gender(rs.getString("m_gender"));

						memVO.setM_bday(rs.getDate("m_bday"));
						memVO.setM_phone(rs.getString("m_phone"));
						memVO.setM_mobile(rs.getString("m_mobile"));
						memVO.setM_zip(rs.getInt("m_zip"));
						memVO.setM_city(rs.getString("m_city"));

						memVO.setM_addr(rs.getString("m_addr"));
						memVO.setM_email(rs.getString("m_email"));
						memVO.setM_word(rs.getString("m_word"));
						memVO.setM_photo(rs.getBytes("m_photo"));
						memVO.setM_source(rs.getInt("m_source"));
						memVO.setM_joindate(DateTool.timestamp2StringMin(rs.getTimestamp("m_joindate")));

						memVO.setM_active(rs.getInt("m_active"));
						memVO.setM_public(rs.getInt("m_public"));
						memVO.setM_bancount(rs.getInt("m_bancount"));
						memVO.setBalance(rs.getInt("balance"));

						list.add(memVO);

					}

			} catch (SQLException e)
			{

				e.printStackTrace();
			} finally
			{
				if (rs != null)
					{
						try
							{
								rs.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

			}

		return list;

	}

	@Override
	public Set<String> getAllUsrId()
	{
		Set<String> set = new TreeSet<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_USERID);
				rs = pstmt.executeQuery();

				while (rs.next()) // rs往下移一筆, 若有值則回傳ture
					{
						set.add(rs.getString("m_accno"));
					}

			} catch (SQLException e)
			{

				e.printStackTrace();
			} finally
			{
				if (rs != null)
					{
						try
							{
								rs.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

			}

		return set;
	}

	@Override
	public Map<String, String> getAllAccounts()
	{

		Map<String, String> map = new TreeMap<String, String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(GETPASSWORD_BYACCNO);
				rs = pstmt.executeQuery();

				while (rs.next()) // rs往下移一筆, 若有值則回傳ture
					{
						map.put(rs.getString("m_accno"), rs.getString("m_psw"));
					}

			} catch (SQLException e)
			{

				e.printStackTrace();
			} finally
			{
				if (rs != null)
					{
						try
							{
								rs.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException e)
							{

								e.printStackTrace();
							}
					}

			}

		return map;

	}

	// 取得會員照片
	@Override
	public byte[] getPhoto(String mem_id)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		byte[] photo = null;

		try
			{

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_PHOTO);

				pstmt.setString(1, mem_id);
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				photo = rs.getBytes(1);

				// 清空裡面參數，重覆使用已取得的PreparedStatement物件
				pstmt.clearParameters();

			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{

				if (pstmt != null)
					{
						try
							{
								pstmt.close();
							} catch (SQLException se)
							{
								System.out.println(se);
							}
					}

				if (con != null)
					{
						try
							{
								con.close();
							} catch (SQLException se)
							{
								System.out.println(se);
							}
					}
			}

		return photo;
	}

}
