/*
 *  1. �U�νƦX�d��-�i�ѫȤ���H�N�W�����Q�d�ߪ����
 *  2. ���F�קK�v�T�į�:
 *        �ҥH�ʺA���͸U��SQL������,���d�ҵL�N�ĥ�MetaData���覡,�]�u�w��ӧO��Table�ۦ���ݭn�ӭӧO�s�@��
 * */

package jdbc.util.CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_MatData {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("ma_status".equals(columnName)) // �Ω��L
			aCondition = columnName + " = " + value;

		else if ("ma_price_down".equals(columnName))
			aCondition = "ma_price between " + value + " and ";

		else if ("ma_price_up".equals(columnName))
			aCondition = value;

		else if ("ma_no".equals(columnName) || "ma_name".equals(columnName) || "ma_ty_no".equals(columnName)) // �Ω�varchar
			aCondition = columnName + " like '%" + value + "%'";

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		
		Set<String> keys = map.keySet();
		for(String key : keys) {
			if(key.equals("ma_price_down") && (map.get(key)[0]).length() == 0) {
				map.put("ma_price_down", new String[] { "0" });	
			}
		}
		
		for(String key : keys) {
			if(key.equals("ma_price_up") && (map.get(key)[0]).length() == 0) {
				map.put("ma_price_up", new String[] { "9999" });	
			}
		}

		StringBuffer whereCondition = new StringBuffer();
		String sb1 = null;
		String sb2 = null;
		int times = 0;
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if ((key.equals("ma_price_down")) || (key.equals("ma_price_up"))) {
					
					count--;
					if (key.equals("ma_price_down")) {
						sb1 = aCondition;
						times++;
					}

					if (key.equals("ma_price_up")) {
						sb2 = aCondition;
						times++;
					}

					if (times == 2) {
						aCondition = sb1 + sb2;
						
						if (count == 0)
							whereCondition.append(" where " + aCondition);
						else
							whereCondition.append(" and " + aCondition);	
					}
				} else {

					if (count == 1)
						whereCondition.append(" where " + aCondition);
					else
						whereCondition.append(" and " + aCondition);

					System.out.println("���e�X�d�߸�ƪ�����count = " + count);
				}
			}
		}

		System.out.println(whereCondition.toString());
		return whereCondition.toString();
	}
}

//	public static void main(String argv[]) {
//
//		// �t�X req.getParameterMap()��k �^��
//		// java.util.Map<java.lang.String,java.lang.String[]> ������
//		Map<String, String[]> map = new TreeMap<String, String[]>();
//		map.put("ma_no", new String[] { "L0001" });
//		map.put("ma_ty_no", new String[] { "T0001" });
//		map.put("ma_name", new String[] { "���J�M" });
//		map.put("ma_price_down", new String[] { "0" });
//		map.put("ma_price_up", new String[] { "500" });
//
//		map.put("ma_status", new String[] { "0" });
//		map.put("action", new String[] { "getXXX" }); // �`�NMap�̭��|�t��action��key
//
//		String finalSQL = "select * from MATERIAL_DATA " + jdbcUtil_CompositeQuery_MatData.get_WhereCondition(map)
//				+ "order by ma_no";
//		System.out.println("����finalSQL = " + finalSQL);
//	}
//}
