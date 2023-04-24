package com.example.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.config.RoutingFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
public class SpringCloudStreamDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamDemoApplication.class, args);
	}

//	@Bean
//	public Consumer<Person> log() {
//		return person -> {
//			System.out.println("Received: " + person);
//		};
//	}

//	@Bean
//	public Function<String, String> uppercase() {
//		return value -> {
//			System.out.println("Received: " + value);
//			return value.toUpperCase();
//		};
//	}
//
//	@Bean
//	public Consumer<ErrorMessage> myErrorHandler() {
//		return v -> {
//			// send SMS notification code
//		};
//	}

	@Bean
	public Function<String, Message<String>> communication() {
		return value -> {
			System.out.println("message received at communication: " + value);
			return MessageBuilder.withPayload(value)
					.setHeader("spring.cloud.stream.sendto.destination", value).build();};
	}

	@Bean
	public Consumer<String> email() {
		return value -> System.out.println("Email: " + value);
	}

	@Bean
	public Consumer<String> sms() {
		return value -> System.out.println("SMS: " + value);
	}

//	@Bean
//	public Function<String, String> reverse() {
//		return value -> {
//			String reverse = new StringBuilder(value).reverse().toString();
//			System.out.println("Reversed: " + reverse);
//			return reverse;
//		};
//	}Þ

	@Bean
	@GlobalChannelInterceptor(patterns = "*")
	public ChannelInterceptor customInterceptor() {
		return new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				System.out.println("Intercepted: " + message);
				return message;
			}
		};
	}

}
