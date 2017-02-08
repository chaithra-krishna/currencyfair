/**
 * 
 */
package com.currency.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @author Macbook pro
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	public void configureMessageBroker(MessageBrokerRegistry config) {
		System.out.println(
				"---------------------------------------------------------WebSocketConfig inside configureMessageBroker");
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	public void registerStompEndpoints(StompEndpointRegistry registry) {
		System.out.println(
				"----------------------------------------------------------WebSocketConfig inside registerStompEndpoints");
		registry.addEndpoint("/ws").withSockJS();
	}

}
