package com.lovin.activeMqJMS.demo1.persist;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * 使用activeMQ 验证消息的持久化
 * 设置了消息的持久化之后，那么接收端对消息时间上就没有依赖性，当发送端发送消息之后，那么接收端暂时可以不用开启服务，当接收端开启服务的时候也是可以接受到
 * 消息发送端的消息的
 * @author Administrator
 *
 */
public class TopicSender {

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
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//启动连接，发送端如果不启动，可以发送消息，但是接收端如果不启动，不会接收到消息
			connection.start(); 
			
			//创建queue，发送地址
			destination = session.createTopic("first_topic"); 
			//创建发送者
			producer = session.createProducer(destination);  
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);  //设定为持久化模式
//			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			for(int i=0;i<5;i++){
				//创建发送的消息内容
				//1、发送text消息
				TextMessage message = session.createTextMessage();
				//2、添加消息内容
				message.setText("我是topic消息: "+i);
				//发送消息
				producer.send(message);
				//提交发送
				session.commit();
				Thread.sleep(3000);
			}
			//关闭session
			session.close();
		} catch (Exception e) {
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
