/**
 * 
 */
package com.currency.rest.services;

import java.io.IOException;

import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.currency.rest.model.CurrencyData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Macbook pro
 *
 */
@RestController
public class CurrencyService implements InitializingBean {

	@Inject
	private ActiveMQConnectionFactory jmsConnectionFactory;

	private Session session = null;

	private MessageProducer producer = null;

	private Connection connection = null;

	@RequestMapping(value = "/countries", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getMsg() {
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
			ObjectMessage message = session.createObjectMessage(currencyData);
			producer.send(message);
			//connection.stop();
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
		Queue queue = session.createQueue("currencyQueue");
		producer = session.createProducer(queue);
	}

}
