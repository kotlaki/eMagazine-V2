package com.kurganov.webserver.interfaces;

import com.kurganov.serverdb.entities.Greeting;

public interface IShopControllerWs {
    void sendMessage(String destination, Greeting message);
}
