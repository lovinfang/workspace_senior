package com.lovin.activeMqJMS.demo1.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 使用ActiveMQ异步接收消息队列中消息的方式
 * @author Administrator
 *
 */
public class QueueReceiverAsync {

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection ;
		final Session session;
		Destination destination;
		final MessageConsumer messageConsumer;
		
		try {
			connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.104:61616");
			connection = connectionFactory.createConnection();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("first_queue");
			messageConsumer = session.createConsumer(destination);
			connection.start();
			messageConsumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					try {
						String mess = message.getStringProperty("messageMapKey");
						if(mess != null){
							System.out.println(mess);
							session.commit();
						}
					} catch (JMSException e) {
						e.printStackTrace();
					}
					
					while(true){
//						MapMessage mapMessage;
//						try {
//							mapMessage = (MapMessage) messageConsumer.receive();
//							if(mapMessage != null){
//								System.out.println(mapMessage.getString("messageMapKey"));
//								session.commit();
//							}else{
//								break;
//							}
//						} catch (JMSException e) {
//							e.printStackTrace();
//						}
						
					}
				}
			});
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
