package com.kurganov.webserver.controllers;

import com.kurganov.serverdb.entities.Greeting;
import com.kurganov.serverdb.entities.Message;
import com.kurganov.webserver.interfaces.IShopControllerWs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ShopControllerWs implements IShopControllerWs {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) throws Exception {
        Thread.sleep(700); // simulated delay
        return new Greeting(message.getName() + " добавлен!");
    }

    public void sendMessage(String destination, Greeting message) {
        simpMessagingTemplate.convertAndSend(destination, message);
    }

}
