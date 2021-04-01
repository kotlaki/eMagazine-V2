package com.kurganov.webserver.interfaces;


import com.kurganov.serverdb.entities.Order;

public interface MailService {

    void sendMail(String email, String subject, String text);

    void sendOrderMail(Order order);

}
