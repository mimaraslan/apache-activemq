package com.mimaraslan.producer;

public interface JmsAbstract {	
	public void connection (String queueName) throws Exception;
	public void send (String payload) throws Exception;
	public void close () throws Exception;
}
