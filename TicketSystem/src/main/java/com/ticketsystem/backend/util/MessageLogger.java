package com.ticketsystem.backend.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MessageLogger {
    private static MessageLogger instance = null;
    private final List<Consumer<String>> subscribers = new ArrayList<>();

    private MessageLogger() {
        subscribe(System.out::println);
    }

    public static MessageLogger getInstance() {
        if (instance == null) {
            instance = new MessageLogger();
        }

        return instance;
    }

    public void subscribe(Consumer<String> subscriber) {
        subscribers.add(subscriber);
    }

    public void log(String message) {
        for (Consumer<String> subscriber : subscribers) {
            subscriber.accept(message);
        }
    }
}
