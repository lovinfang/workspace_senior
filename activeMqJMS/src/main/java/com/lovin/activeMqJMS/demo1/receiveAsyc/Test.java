package com.lovin.activeMqJMS.demo1.receiveAsyc;

import javax.jms.JMSException;

public class Test {

	public static void main(String[] args) throws JMSException, Exception{
		 JmsSender sender = new JmsSender();
		 JmsReceiver receiver = new JmsReceiver();
		  
		 sender.sendMessage("object");
		 sender.close();
		  
		 receiver.receiveMessage();
		 receiver.close();
	}
}
