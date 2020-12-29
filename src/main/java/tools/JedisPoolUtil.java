package tools;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//20201030 By YCL, �ѦҤp�d�ѮvJDBC�M��idv.david.websocketchat.jedis���e
public class JedisPoolUtil {
	
	//(L)�u�ݭn�@�ӳs�u���A�ҥH�ŧi��satic
	private static JedisPool pool = null;

	private JedisPoolUtil() {
	}

	public static JedisPool getJedisPool() {
		if (pool == null) {
			synchronized (JedisPoolUtil.class) {
				 //(L)�S���s�u���ɤ~�إ�
				if (pool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(8);
					config.setMaxIdle(8);
					config.setMaxWaitMillis(10000); //(L)�s�u���ɵ���10��
					pool = new JedisPool(config, "localhost", 6379); //(L)sid=localhost�Bport=6379�ݹ��redis�����]�w
				}
			}
		}
		return pool;
	}

	// �i�bServletContextListener��contextDestroyed�̩I�s����k���PRedis�s�u��
	public static void shutdownJedisPool() {
		if (pool != null)
			pool.destroy();
	}
}
