package tools;

import com.wel_record.model.WelRecordJDBCDAO;
import com.wel_record.model.WelRecordVO;

public class FakeDataMaker
{

	public static void main(String[] args)
	{
		// ��09-11�̧ǦU�]�@��
		String month = "09";
//		String month = "10";
//		String month = "11";

		int memNumber = 15;

		// --------------------------�����-�H���s�Wn���������----------------------------------
		WelRecordJDBCDAO welRecordDAO = new WelRecordJDBCDAO();
//		int[] srcArray = { 10, 20, 30, 31, 32, 33, 34, 35, 36, 37, 38, 40, 41, 42, 43 };
		String[] memArray = new String[memNumber];

		for (int i = 0; i < memNumber; i++)
			{

				memArray[i] = "M" + String.format("%06d", i + 1);
				System.out.println(memArray[i]);
			}

		int n = 30;
		for (int i = 0; i < n; i++)
			{
				System.out.println(i);
				// �H��������ӷ�srcArray&memArray���޸�
				int r = (int) (Math.random() * 100 + 1); // �H������@�Ʀr(1-100��)
				int memIndex = (int) (Math.random() * memNumber);
				int amount = ((int) (Math.random() * 100) + 1) * 100;
				int amountShare = (int) (amount * 0.15);
				int srcIndex = 0;

				int sec = ((int) (Math.random() * 60) + 1);
				int min = ((int) (Math.random() * 60) + 1);
				int hour = ((int) (Math.random() * 12) + 1);

				WelRecordVO welRecordVO2 = new WelRecordVO();
				welRecordVO2.setMem_id(memArray[memIndex]);
				welRecordVO2.setOrder_id(null);

				if ((r > 0 && r <= 38)) // �x��
					{
						welRecordVO2.setTns_src(10);
						welRecordVO2.setTns_amount(amount);

					} else if (r > 38 && r <= 48) // ����
					{
						welRecordVO2.setTns_src(20);
						welRecordVO2.setTns_amount(-amount);

					} else if (r > 48 && r <= 53) // ���x�h��

					{
						srcIndex = (int) (Math.random() * 3 + 35); // 35-38������@�Ʀr
						welRecordVO2.setTns_src(srcIndex);
						welRecordVO2.setTns_amount((int) (amount * 0.3)); // �h�ڪ��B������x�ȤT��

					} else if (r > 63 && r <= 73) // ����+�馩��

					{
						srcIndex = (int) (Math.random() * 4 + 30); // 30-34������@�Ʀr
						welRecordVO2.setTns_src(srcIndex);

						welRecordVO2.setTns_amount(amountShare);

					} else
					{
						srcIndex = (int) (Math.random() * 3 + 40); // 40-43������@�Ʀr

						if (srcIndex == 40)
							{
								welRecordVO2.setOrder_id("S2020" + month + "12-00002");
							} else if (srcIndex == 41)
							{
								welRecordVO2.setOrder_id("P2020" + month + "07-00009");
							} else if (srcIndex == 42)
							{
								welRecordVO2.setOrder_id("B2020" + month + "23-00008");
							} else if (srcIndex == 43)
							{
								welRecordVO2.setOrder_id("M2020" + month + "10-00004");
							}
						welRecordVO2.setTns_src(srcIndex);
						welRecordVO2.setTns_amount((int) (-amount * 0.8)); // ���x����
					}

				welRecordVO2.setTns_time("2020-" + month + "-02 " + hour + ":" + min + ":" + sec);

				welRecordDAO.insert4FakeData(welRecordVO2);
			}

//		10:�|���x��
//		20:�|������
//		30:���x����-�@���ʶR����
//		31:���x����-�w�ʤ���
//		32:���x����-�v�Ф���
//		33:���x����-�����|����
//		34:���x����-�w�ʧ馩��
//		35:���x�h��-�@���ʶR�h��
//		36:���x�h��-�w��
//		37:���x�h��-�v��
//		38:���x�h��-�����|
//		40:���x����-�@���ʶR�q��
//		41:���x����-�w�ʭq��
//		42:���x����-�v�Эq��
//		43:���x����-�����|

	}

}
