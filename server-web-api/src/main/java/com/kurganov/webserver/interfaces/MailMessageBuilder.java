package com.kurganov.webserver.interfaces;

import com.kurganov.serverdb.entities.Order;

public interface MailMessageBuilder {

    String buildOrderEmail(Order order);

}
