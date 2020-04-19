package com.lovin.activeMqJMS.demo1.topic;

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
 * 使用activeMQ发送消息 topic 消息订阅
 * 消息订阅模式的话，是有一个时间上的依赖性，所以如果发送端发送消息给接收端，如果接收端没有开启，那么消息就会丢失，此时是非持久化模式，所以要先开启消息接收端
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
			
			//创建发送的消息内容
			//1、发送text消息
			/*
			TextMessage message = session.createTextMessage("我的第一次消息发送");
			//设置消息属性
			message.setStringProperty("key", "hello activeMQ"); 
			//发送消息
			producer.send(message);
			*/
			
			//2、发送map类型的消息
			MapMessage mapMessage = session.createMapMessage();
			for(int i=0;i<5;i++){
				mapMessage.setString("topicMapKey", "mapMessageValue"+i);
				producer.send(mapMessage);
			}
			
			
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
