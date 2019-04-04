package com.mimaraslan.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ActiveMQ implements JmsAbstract {

	private static final Logger LOGGER = Logger.getLogger(ActiveMQ.class);
	private static final String URL_BROKER = "tcp://127.0.0.1:61616";
	public static final String QUEUE_JMS_TEST = "queueJmsTest";

	private String payload;
	private ConnectionFactory connectionFactory;
	private Destination destination;
	private Connection connection;
	private Session session;
	private Message message;
	private MessageProducer producer;

	public ActiveMQ() {
		this.payload = "";
	}

	public void connection(String queueName) throws Exception {
		LOGGER.log(Level.INFO, "ActiveMQ -> queue: " + queueName + ", url broker" + URL_BROKER);
		this.connectionFactory = new ActiveMQConnectionFactory(URL_BROKER);
		this.destination = new ActiveMQQueue(queueName);
		this.connection = this.connectionFactory.createConnection();
		this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public void send(String payload) throws Exception {
		LOGGER.log(Level.INFO, "ActiveMQ -> payload: " + payload);
		this.payload = payload;
		this.message = session.createTextMessage(this.payload);
		this.producer = session.createProducer(destination);
		producer.send(message);
	}

	public void close() throws Exception {
		LOGGER.log(Level.INFO, "ActiveMQ -> close connection");

		if (this.session != null) {
			this.session.close();
		}

		if (this.connection != null) {
			this.connection.close();
		}
	}

}
