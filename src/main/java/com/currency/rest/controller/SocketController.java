/**
 * 
 */
package com.currency.rest.controller;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.currency.rest.model.Greeting;
import com.currency.rest.model.HelloMessage;

/**
 * @author Macbook pro
 *
 */
@Controller
public class SocketController {

	@MessageMapping("/greeting")
	@SendTo("/topic/greetings")
	@RequestMapping("/hello")
	public Greeting greeting(HelloMessage message) throws Exception {
		System.out.println("MessageController====================================>Client connection");
		return new Greeting("[" + (new Date()) + "]  The server returns: Hello, the client enters the message < "
				+ message.getName() + ">");
	}

	@RequestMapping("/getmessage")
	public String getMessage() {
		System.out.println("************inside hello*****************");
		return "temp";
	}
}
