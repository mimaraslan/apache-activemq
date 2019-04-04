package com.mimaraslan;

import com.mimaraslan.producer.ActiveMQ;

public class ActiveMQProducerApp {

	public static void main(String[] args) throws Exception {

		ActiveMQ activeMQ = new ActiveMQ();

		activeMQ.connection(ActiveMQ.QUEUE_JMS_TEST);

		for (int i = 1; i <= 500000; i++) {
		    try{Thread.sleep(200);}catch(InterruptedException e){System.out.println(e);}  
			activeMQ.send("LOLO selam soyledi." + i);
		}

		activeMQ.close();
	}

}
