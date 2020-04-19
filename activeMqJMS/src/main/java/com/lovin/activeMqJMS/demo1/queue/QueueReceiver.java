package com.lovin.activeMqJMS.demo1.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 测试接收ActiveMQ的Queue消息 PTP
 * ActiveMQ同步接受消息的方式
 * @author Administrator
 *
 */
public class QueueReceiver {
	
	public static void main(String[] args) {
		ConnectionFactory connectionFactory; //连接工厂
		Connection connection = null; //客户端到JMS provider的连接
		Session session; //一个发送或接受消息的会话
		Destination destination; //消息的目的
		MessageConsumer consumer; //消息接受者
		
		try {
			//通过tcp协议连接
			connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.104:61616"); 
			//创建连接
			connection = connectionFactory.createConnection(); 
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
//			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			
			//创建queue，发送地址
			destination = session.createQueue("first_queue"); 
			//创建接受者
			consumer = session.createConsumer(destination);  
			
			//启动连接，如果不启动连接是无法接受到queue的消息的
			connection.start();

			//接受消息
			/*
			 这样执行完之后只能接受到一次消息，我们使用while(true)进行实时监听
			 TextMessage message = (TextMessage) consumer.receive();
			 System.out.println(message.getText());
			*/
			while(true){
				/* 接收TextMessage类型的消息 */
//				TextMessage message = (TextMessage) consumer.receive();
//				if(message != null){
//					System.out.println(message.getText()+"->"+message.getStringProperty("key"));
//					//如果此时接受者接收到消息的时候不commit，那么相当于没有给发送端进行确认，消息不会到Messages Dequeued  里面，可以重复接收
//					session.commit();
//				}else{
//					break;
//				}
				
				//接收MapMessage类型的消息,接收方一定要跟发送方约定好一个消息的格式
				MapMessage mapMessage = (MapMessage) consumer.receive();
				if(mapMessage != null){
					System.out.println(mapMessage.getString("messageMapKey"));
					session.commit();
//					mapMessage.acknowledge();
				}else{
					break;
				}
			}
			
			//提交接收
//			session.commit();
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
