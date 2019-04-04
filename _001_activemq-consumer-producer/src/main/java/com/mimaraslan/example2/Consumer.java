package com.mimaraslan.example2;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer{
// URL of the JMS server
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
// default broker URL is : tcp://localhost:61616"

// Name of the queue we will receive messages from
	private static String subject = "MYTESTQUEUE";

	public static void main(String[] args) throws JMSException {
// Getting JMS connection from the server
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

// Creating session for seding messages
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

// Getting the queue 'MYTESTQUEUE'
		Destination destination = session.createQueue(subject);

// MessageConsumer is used for receiving (consuming) messages
		MessageConsumer consumer = session.createConsumer(destination);

		   
		   for (;;) {
	            try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}  

	         // Here we receive the message.
	         // By default this call is blocking, which means it will wait
	         // for a message to arrive on the queue.
	         		Message message = consumer.receive();
	           // System.out.println("Waiting for message.");
	            if (message == null) {
	                break;
	            }
	            
	         // There are many types of Message and TextMessage
	         // is just one of them. Producer sent us a TextMessage
	         // so we must cast to it to get access to its .getText()
	         // method.
	         		   if (message instanceof TextMessage) {
	         			  System.out.println("Got message: " + message);
		      	            TextMessage textMessage = (TextMessage) message;
		      				System.out.println("Receivedage " + textMessage.getText());
	         			}	   
	          
	        }
		  connection.close();		  
	}
}