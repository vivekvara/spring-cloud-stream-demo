package com.example.stream;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ValuesConsumer {

    @Bean
    public Consumer<String> onReceive() {
        return (message) -> {
            System.out.println("Received the value: " + message);
        };
    }
}