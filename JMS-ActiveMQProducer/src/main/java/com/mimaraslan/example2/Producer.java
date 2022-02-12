package com.mimaraslan.example2;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
// URL of the JMS server. DEFAULT_BROKER_URL will just mean
// that JMS server is on localhost
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
// default broker URL is : tcp://localhost:61616"

	private static String subject = "MYTESTQUEUE"; // Queue Name
// You can create any/many queue names as per your requirement.

	public static void main(String[] args) throws JMSException {
// Getting JMS connection from the server and starting it
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
// JMS messages are sent and received using a Session. We will
// create here a non-transactional session object. If you want
// to use transactions you should set the first parameter to 'true'
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
// Destination represents here our queue 'VALLYSOFTQ' on the
// JMS server. You don't have to do anything special on the
// server to create it, it will be created automatically.
		Destination destination = session.createQueue(subject);
// MessageProducer is used for sending messages (as opposed
// to MessageConsumer which is used for receiving them)
		MessageProducer producer = session.createProducer(destination);
// We will send a small text message saying 'Hello' in Japanese
		TextMessage message = session.createTextMessage("DENEME MESAJI");
// Here we are sending the message!
		//producer.send(message);
		//System.out.println(message.getText());
		
		for (int i = 1; i <= 5000; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
			message = session.createTextMessage("LOLO sana selam soyledi. "+i);
			producer.send(message);
			System.out.println(message.getText());
		}

		connection.close();
	}
}