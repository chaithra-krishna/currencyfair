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
	public String greeting(HelloMessage message) throws Exception {
		System.out.println("MessageController====================================>Client connection");
		return "";
	}

	@RequestMapping("/getmessage")
	public String getMessage() {
		System.out.println("************inside getMessage*****************");
		return "temp";
	}

	@RequestMapping("/test")
	public String test() {
		System.out.println("************inside test*****************");
		return "test";
	}

	@RequestMapping("/test1")
	public String test1() {
		System.out.println("************inside test1*****************");
		return "hello";
	}

	@RequestMapping("/test2")
	public String test2() {
		System.out.println("************inside test2*****************");
		return "index";
	}
}
