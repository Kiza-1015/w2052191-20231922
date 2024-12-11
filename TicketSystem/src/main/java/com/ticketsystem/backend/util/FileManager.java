package com.ticketsystem.backend.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ticketsystem.backend.model.Configuration;
import com.ticketsystem.backend.model.Ticket;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    private static final String configFile = "config.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final String loggerFile = "Logger.log";
    private static final Logger logger = Logger.getLogger(FileManager.class.getName());

    public static void saveConfiguration(Configuration config) {
        try {
            Writer writer = new FileWriter(configFile);
            gson.toJson(config, writer);
            writer.close();

            System.out.println("Successfully saved the configuration");
        } catch (IOException e) {
            System.out.println("Failed to save configuration: " + e.getMessage());
        }
    }

    public static Configuration getConfiguration() {
        try (FileReader reader = new FileReader(configFile)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.out.println("Failed to load the configuration: " + e.getMessage());
            ;
            return null;
        }
    }

    public static void saveLogs(String transactionType, Ticket ticket, List<Ticket> tickets) {
        try {
            SimpleDateFormat simpleData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = simpleData.format(new Date());

            String log = String.format("%s - %s: Ticket ID %s, Thread: %s, Pool Size: %d",
                    timestamp,
                    transactionType,
                    ticket.getTicketId(),
                    Thread.currentThread().getName(),
                    tickets.size()
            );

            try (PrintWriter writer = new PrintWriter(new FileWriter(loggerFile, true))) {
                writer.println(log);
            }

            System.out.println("Successfully saved the logs");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save logs: " + e.getMessage());
            ;
        }
    }
}

