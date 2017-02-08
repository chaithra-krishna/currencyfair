/**
 * 
 */
package com.currency.rest.config;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.currency.rest.model.CurrencyData;

/**
 * @author Macbook pro
 *
 */
@Component
public class MessageReceiver {

	@Autowired
	private SimpMessagingTemplate template;
	
	// static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);
	private static final String ORDER_RESPONSE_QUEUE = "currencyQueue";

	@JmsListener(destination = ORDER_RESPONSE_QUEUE)
	public void receiveMessage(final Message<CurrencyData> message) throws JMSException {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		// LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		MessageHeaders headers = message.getHeaders();
		// LOG.info("Application : headers received : {}", headers);
		System.out.println("Application : headers received : {} " + headers);
		CurrencyData response = message.getPayload();
		System.out.println("Application : response received : {}" + response);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		this.template.convertAndSend("/topic/greetings", response);
		// LOG.info("Application : response received : {}", response);
		// LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
}
