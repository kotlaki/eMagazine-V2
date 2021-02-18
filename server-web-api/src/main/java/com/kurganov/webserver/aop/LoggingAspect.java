package com.kurganov.webserver.aop;

import com.kurganov.serverdb.entities.OrderItem;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {

    Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("within(com.kurganov.webserver.utils.ShoppingCart)")
    public void stringProcessingMethods() {
    };

    @After("stringProcessingMethods()")
    public void logMethodCall(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        logger.log(Level.INFO, "@After: Название метода: " + methodName);
    }

    @AfterReturning(pointcut = "execution(public * com.kurganov.webserver.controllers.ShoppingCartController.*(..))",
            returning = "result")
    public void logAfterReturning(JoinPoint jp, Object result) {
        String methodName = jp.getSignature().getName();
        logger.log(Level.INFO, "@AfterReturning: Название метода: " + methodName + " Возвращенное значение: " + result.toString());
    }

}
