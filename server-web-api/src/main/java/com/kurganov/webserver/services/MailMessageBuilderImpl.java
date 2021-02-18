package com.kurganov.webserver.services;

import com.kurganov.serverdb.entities.Order;
import com.kurganov.webserver.interfaces.MailMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailMessageBuilderImpl implements MailMessageBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildOrderEmail(Order order) {
        Context context = new Context();
        context.setVariable("order", order);
        return templateEngine.process("order-mail", context);
    }
}
