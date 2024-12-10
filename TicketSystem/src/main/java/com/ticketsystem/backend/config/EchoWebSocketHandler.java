package com.ticketsystem.backend.config;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;

public class EchoWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            // Echo the received message back to the client
            session.sendMessage(new TextMessage("Echo: " + message.getPayload()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

