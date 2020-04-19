package com.lovin.activeMqJMS.demo1.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * 使用activeMQ发送消息 queue
 * @author Administrator
 *
 */
public class QueueSender {

	public static void main(String[] args) {
		
		ConnectionFactory connectionFactory; //连接工厂
		Connection connection = null; //客户端到JMS provider的连接
		Session session; //一个发送或接受消息的会话
		Destination destination; //消息的目的
		MessageProducer producer; //消息发送者
		
		try {
			//通过tcp协议连接
			connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.104:61616"); 
			//创建连接
			connection = connectionFactory.createConnection(); 
			/**
			 * session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE); 表示需要事务，需要客户端来进行确认
			 * session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        	 * session.commit();
        	 * 
        	 * session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE); 表示需要事务，客户端确认方式为：
        	 * session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE); 客户端不需要事务
        	 * mapMessage.acknowledge(); 来确认，而不是  session.commit();
			 */
//			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
			//启动连接，发送端如果不启动，可以发送消息，但是接收端如果不启动，不会接收到消息
			connection.start(); 
			
			//创建queue，发送地址
			destination = session.createQueue("first_queue"); 
			//创建发送者
			producer = session.createProducer(destination);  
			
			//创建发送的消息内容
			//1、发送text消息
			/*TextMessage message = session.createTextMessage("我的第一次消息发送");
			//设置消息属性
			message.setStringProperty("key", "hello activeMQ"); 
			//发送消息
			producer.send(message);*/
			
			//2、发送map类型的消息
			MapMessage mapMessage = session.createMapMessage();
			for(int i=0;i<5;i++){
				mapMessage.setString("messageMapKey", "mapMessageValue"+i);
				producer.send(mapMessage);
			}
			
//			session.rollback();   //相当于回滚事务
			//提交发送
			session.commit();
			//关闭session
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally{
			//关闭连接
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
	}
}
