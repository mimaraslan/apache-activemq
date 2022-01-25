package com.mimaraslan;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQXAConnectionFactory;

import javax.jms.*;

public class Application {

    public ConnectionFactory createConnectionFactory() {
        return new ActiveMQConnectionFactory(
                "tcp://localhost:61616"
        );
    }

    public XAConnectionFactory createXAConnectionFactory() {
        return new ActiveMQXAConnectionFactory(
                "tcp://localhost:61616"
        );
    }

    public QueueConnectionFactory createQueueConnectionFactory() {
        return new ActiveMQConnectionFactory(
                "tcp://localhost:61616"
        );
    }

    public XAQueueConnectionFactory createXAQueueConnectionFactory() {
        return new ActiveMQXAConnectionFactory(
                "tcp://localhost:61616"
        );
    }

    public TopicConnectionFactory createTopicConnectionFactory() {
        return new ActiveMQConnectionFactory(
                "tcp://localhost:61616"
        );
    }

    public XATopicConnectionFactory createXATopicConnectionFactory() {
        return new ActiveMQXAConnectionFactory(
                "tcp://localhost:61616"
        );
    }

//----------------------------------------------------------------------------

    public Connection createConnection(ConnectionFactory cf)
            throws JMSException {
        return cf.createConnection();
    }

    public XAConnection createXAConnection(XAConnectionFactory cf)
            throws JMSException {
        return cf.createXAConnection();
    }


    public QueueConnection createQueueConnection(QueueConnectionFactory cf)
            throws JMSException {
        return cf.createQueueConnection();
    }

    public XAQueueConnection createXAQueueConnection(XAQueueConnectionFactory cf)
            throws JMSException {
        return cf.createXAQueueConnection();
    }


    public TopicConnection createTopicConnection(TopicConnectionFactory cf)
            throws JMSException {
        return cf.createTopicConnection();
    }

    public XATopicConnection createXATopicConnection(XATopicConnectionFactory cf)
            throws JMSException {
        return cf.createXATopicConnection();
    }

//----------------------------------------------------------------------------

    public Session createSession(Connection connection)
            throws JMSException {

        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public XASession createXASession(XAConnection connection)
            throws JMSException {
        return connection.createXASession();
    }

    public QueueSession createQueueSession(QueueConnection connection)
            throws JMSException {
        return connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public XAQueueSession createXAQueueSession(XAQueueConnection connection)
            throws JMSException {
        return connection.createXAQueueSession();
    }

    public TopicSession createTopicSession(TopicConnection connection)
            throws JMSException {
        return connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public XATopicSession createXATopicSession(XATopicConnection connection)
            throws JMSException {
        return connection.createXATopicSession();
    }

    /*//DEMO 1
    public void sendTextMessageToQueue(String message,
                                       Session session) throws JMSException {
        Queue queue = session.createQueue("TEST_DESTINATION");
        TextMessage msg = session.createTextMessage(message);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.send(msg);
    }

    //DEMO 2
    public void sendTextMessageToQueue(String message,
                                       QueueSession session) throws JMSException {
        Queue queue = session.createQueue("TEST_DESTINATION");
        TextMessage msg = session.createTextMessage(message);
        QueueSender messageProducer = session.createSender(queue);
        messageProducer.send(msg);
    }


     //DEMO 3
    public void sendTextMessageToTopic(String message,
                                       Session session) throws JMSException {
        Topic queue = session.createTopic("TEST_TOPIC");
        TextMessage msg = session.createTextMessage(message);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.send(msg);
    }



     //DEMO 4
    public void sendTextMessageToTopic(String message,
                                       TopicSession session) throws JMSException {
        Topic topic = session.createTopic("TEST_TOPIC");
        TextMessage msg = session.createTextMessage(message);
        TopicPublisher topicPublisher = session.createPublisher(topic);
        topicPublisher.send(msg);
    }


     //DEMO 5
    public MessageConsumer consumeFromQueue(Session session,
                                            String destination,
                                            MessageListener messageListener)
            throws JMSException {
        Queue queue = session.createQueue(destination);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(messageListener);
        return consumer;
    }
    */

    //DEMO 6
    public TopicSubscriber consumeFromTopic(Session session,
                                            String destination,
                                            MessageListener messageListener)
            throws JMSException {
        Topic topic = session.createTopic(destination);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic,
                "test-subscription");
        topicSubscriber.setMessageListener(messageListener);
        return topicSubscriber;
    }



    public void sendTextMessageToTopic(String message,
                                       Session session) throws JMSException {
        Topic queue = session.createTopic("TEST_TOPIC");
        TextMessage msg = session.createTextMessage(message);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.setPriority(9); //0-9, 9 highest, all messages, 4 default
        messageProducer.setTimeToLive(10000); //milliseconds, 0 default - doesn't expire
        messageProducer.send(msg,
                DeliveryMode.NON_PERSISTENT,
                9, // Per message
                20000); //Per message
    }


    public static void main(String... args) throws Exception {
       /* //DEMO 1
        Application app = new Application();
        ConnectionFactory cf = app.createConnectionFactory();
        Connection conn = app.createConnection(cf);
        Session session = app.createSession(conn);
        app.sendTextMessageToQueue("Test Message", session);
        session.close();
        conn.close();

         //DEMO 2
        Application app = new Application();
        QueueConnectionFactory cf = app.createQueueConnectionFactory();
        QueueConnection conn = app.createQueueConnection(cf);
        QueueSession session = app.createQueueSession(conn);
        app.sendTextMessageToQueue("Another Message", session);
        session.close();
        conn.close();

         //DEMO 3
        Application app = new Application();
        ConnectionFactory cf = app.createConnectionFactory();
        Connection conn = app.createConnection(cf);
        Session session = app.createSession(conn);
        app.sendTextMessageToTopic("Test Message", session);
        session.close();
        conn.close();

         //DEMO 4
        Application app = new Application();
        TopicConnectionFactory cf = app.createTopicConnectionFactory();
        TopicConnection conn = app.createTopicConnection(cf);
        TopicSession session = app.createTopicSession(conn);
        app.sendTextMessageToTopic("Test Message", session);
        session.close();
        conn.close();


        //DEMO 5
        Application app = new Application();
        ConnectionFactory cf = app.createConnectionFactory();
        Connection conn = app.createConnection(cf);
        Session session = app.createSession(conn);
        MessageConsumer consumer = app.consumeFromQueue(session,
                "TEST_QUEUE",
                (message -> {
                    //Do something with message
                }));
        conn.start();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {

                try {
                    super.run();
                    conn.stop();
                    consumer.close();
                    session.close();
                    conn.close();

                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

    }
        */



        //DEMO 6
        Application app = new Application();
        ConnectionFactory cf = app.createConnectionFactory();
        Connection conn = app.createConnection(cf);
        Session session = app.createSession(conn);
        conn.setClientID("MyUniqueClientId");
        TopicSubscriber topicSubscriber =
                app.consumeFromTopic(session,
                        "TEST_TOPIC",
                        (message -> {
                            if (message instanceof TextMessage) {
                                TextMessage txtMsg = (TextMessage)message;
                                try {
                                    System.out.println(txtMsg.getText());
                                } catch (JMSException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
        conn.start();

        //Free resources
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    conn.stop();
                    topicSubscriber.close();
                    session.close();
                    conn.close();

                    //If you are finished with the subscription
                    session.unsubscribe("test-subscription");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        while (true) {
            app.sendTextMessageToTopic("Test Message", session);
        }

    }

}
