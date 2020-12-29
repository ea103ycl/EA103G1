package tools;

import javax.servlet.http.HttpSession;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.wel_record.model.WelRecordService;

public class MoneyTool
{

	public static boolean checkOut(HttpSession session, int tns_src, String order_id, int amount)
	{

		MemVO memVO = (MemVO) session.getAttribute("memVO");

		MemService memSrc = new MemService();

		String mem_id = memVO.getMem_id();

		int balance = memSrc.findByPrimaryKey(mem_id).getBalance();// ���s�bDB���@���ӷ|���l�B

		if (amount + balance < 0 && amount < 0) // �Y������(amount < 0)�B�l�B����
			{
				return false;

			} else // ���ھl�B�����ά��x�Ȯ�
			{
				// �s�W��ƶiDB��
				WelRecordService wrSrc = new WelRecordService();
				wrSrc.addWelRecord(mem_id, tns_src, order_id, amount);

				// �A�qDB���X�ç�s�ثesession��MemVO
				MemVO updatedMemVO = memSrc.findByPrimaryKey(mem_id);
				
				session.setAttribute("memVO", updatedMemVO);
				return true;
			}

	}
	public static boolean checkOut_back(String mem_id, int tns_src, String order_id, int amount)
	{

		MemService memSrc = new MemService();

		int balance = memSrc.findByPrimaryKey(mem_id).getBalance();// ���s�bDB���@���ӷ|���l�B

		if (amount + balance < 0 && amount < 0) // �Y������(amount < 0)�B�l�B����
			{
				return false;

			} else // ���ھl�B�����ά��x�Ȯ�
			{
				// �s�W��ƶiDB��
				WelRecordService wrSrc = new WelRecordService();
				wrSrc.addWelRecord(mem_id, tns_src, order_id, amount);

				return true;
			}

	}
}
