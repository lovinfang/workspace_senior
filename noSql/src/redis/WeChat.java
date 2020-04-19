package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class WeChat  extends JedisPubSub{

	/**
	 * ����Ϣ����ʱ
	 */
	@Override
	public void onMessage(String channel, String message) {
		System.out.println(channel + ":" + message);
	}

	@Override
	public void onPMessage(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPSubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ��������
	 */
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out.println(channel + ":" + subscribedChannels);
	}

	@Override
	public void onUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		Jedis je  = null;
		try {
			WeChat wc = new WeChat();
			je = new Jedis("192.168.20.103",6379,0);  //0 Ϊ��Чʱ��
			// je.auth("lovinfang"); //���redis���������룬��Ҫ�����¼
			wc.proceed(je.getClient(), "lovin-fang");  //����
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(je != null){
				je.disconnect();   //�ǵ��ͷ�����
			}
		}
	}
}
