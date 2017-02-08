/**
 * 
 */
package com.currency.rest.services;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.currency.rest.model.CurrencyData;
import com.currency.rest.model.HelloMessage;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Macbook pro
 *
 */
@RestController
public class CurrencyService implements InitializingBean, Runnable {

	@Inject
	private ActiveMQConnectionFactory jmsConnectionFactory;

	private Session session = null;

	private MessageProducer producer = null;

	private Connection connection = null;

	private Queue queue = null;

	@Autowired
	private SimpMessagingTemplate template;

	@RequestMapping(value = "/countries", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getMsg() {
		HelloMessage helloMessage = new HelloMessage();
		helloMessage.setName("test12344");
		this.template.convertAndSend("/topic/greetings", helloMessage);
		return "Hello World !! - Jersey 2";
	}

	@RequestMapping(value = "/messagepost", method = RequestMethod.POST, headers = "Accept=application/json")
	public String createTrackInJSON(@RequestBody String string) {
		System.out.println("service called");
		String result = "Curreny data added successfully";
		ObjectMapper mapper = new ObjectMapper();
		CurrencyData currencyData = null;
		try {
			currencyData = mapper.readValue(string, CurrencyData.class);
			System.out.println("currencyData = " + currencyData);
			if (null != currencyData) {
				subbmitToQueue(currencyData);
			}
		} catch (JsonParseException e) {
			result = "not valid input - JsonParseException occurred";
			e.printStackTrace();
		} catch (JsonMappingException e) {
			result = "not valid input - JsonMappingException occurred";
			e.printStackTrace();
		} catch (IOException e) {
			result = "not valid input - IOException occurred";
			e.printStackTrace();
		}

		System.out.println("result = " + result);
		return result;

	}

	public void subbmitToQueue(CurrencyData currencyData) {
		try {
			ObjectMessage ObjectMessage = session.createObjectMessage(currencyData);
			producer.send(ObjectMessage);
			// connection.stop();
			System.out.println("Uploaded to queue");
		} catch (JMSException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void afterPropertiesSet() throws Exception {
		System.out.println("**************************************************************************************");
		connection = jmsConnectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = session.createQueue("currencyQueue");
		producer = session.createProducer(queue);

		try {
			MessageConsumer consumer = session.createConsumer(queue);

			while (true) {
				ObjectMessage ObjectMessage = (javax.jms.ObjectMessage) consumer.receive();
				System.out.println("***************************************");
				System.out.println(ObjectMessage);
				CurrencyData currency = (CurrencyData) ObjectMessage.getObject();
				this.template.convertAndSend("/topic/greetings", currency);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	public List<Object> retriveFromQueue() {
		List<Object> objects = null;
		try {
			MessageConsumer consumer = session.createConsumer(queue);

			while (true) {
				ObjectMessage ObjectMessage = (javax.jms.ObjectMessage) consumer.receive();
				System.out.println("***************************************");
				System.out.println(ObjectMessage);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
		return objects;
	}

	public static void main(String[] args) {

		Runnable runable = new CurrencyService();
		Thread t = new Thread(runable);
		t.start();
	}

	public void run() {
		Session session = null;
		// MessageProducer producer = null;
		Connection connection = null;
		Queue queue = null;
		try {
			ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = jmsConnectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = session.createQueue("currencyQueue");

			MessageConsumer consumer = session.createConsumer(queue);

			while (true) {
				ObjectMessage ObjectMessage = (javax.jms.ObjectMessage) consumer.receive();
				System.out.println("***************************************");
				System.out.println(ObjectMessage.getObject());
				CurrencyData currency = (CurrencyData) ObjectMessage.getObject();
				System.out.println(currency);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
