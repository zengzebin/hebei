package com.ssxt.activeMQ;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

public class QueueMessageListener implements MessageListener {

	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {

			TextMessage tm = (TextMessage) message;
			String text = tm.getText();

			System.out.println("点对点QueueReceiver1监听器" + text);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}