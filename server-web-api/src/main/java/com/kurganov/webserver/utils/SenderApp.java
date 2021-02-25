package com.kurganov.webserver.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class SenderApp {
    private final static String QUEUE_NAME = "shopcart";
    public void senderApp() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String msg = "Товар добавлен в корзину!";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("sent " + msg);
        }
    }
}
