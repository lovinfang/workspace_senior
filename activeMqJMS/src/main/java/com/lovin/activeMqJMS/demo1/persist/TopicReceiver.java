package com.lovin.activeMqJMS.demo1.persist;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 测试ActiveMQ接收 topic消息订阅
 * @author Administrator
 *
 */
public class TopicReceiver {
	
	public static void main(String[] args) {
		ConnectionFactory connectionFactory; //连接工厂
		Connection connection = null; //客户端到JMS provider的连接
		Session session; //一个发送或接受消息的会话
//		Destination destination; //消息的目的
//		MessageConsumer consumer; //消息接受者
		
		try {
			//通过tcp协议连接
			connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.104:61616"); 
			//创建连接
			connection = connectionFactory.createConnection(); 
			//持久化订阅
			connection.setClientID("client1");//注册一个唯一client id到这个topic
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//启动连接，如果不启动连接是无法接受到queue的消息的
			connection.start();
			Topic destination = session.createTopic("first_topic");
			TopicSubscriber consumer = session.createDurableSubscriber(destination, "topic1"); //topic1 为客户端名称 随意写
			
			int i = 0;
			while(true){
				Message message = consumer.receive();
				if(message == null){
					continue;
				}
				i++;
				TextMessage tm = (TextMessage) message;
				System.out.println(tm.getText());
//				session.rollback();   消息在提交之前是可以回滚的，这个时候消息就不会发送到队列中，activeMQ不是基于分布式事务，是基于本地事务
				session.commit();
				if(i>=5){
					break;
				}
			}
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
