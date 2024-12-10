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


    /**
     * Saves the provided {@code Configuration} object to a file.
     *
     * <p>This method serializes the given {@code Configuration} object into JSON format and writes it to a file
     * specified by {@code configFile}. If the file does not exist, it will be created. The method ensures that
     * the configuration is properly saved and reports success or failure to the console.</p>
     *
     * @param config the {@code Configuration} object to be saved.
     * @throws IOException if an error occurs during file writing or closing the writer.
     */
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

    /**
     * Loads and deserializes the configuration from a file.
     *
     * <p>This method reads the configuration file specified by {@code configFile} and deserializes
     * the JSON content into a {@code Configuration} object. If the file is successfully read,
     * the configuration is returned. If an error occurs during the file reading process, an
     * error message is printed, and {@code null} is returned.</p>
     *
     * @return the {@code Configuration} object loaded from the file, or {@code null} if an error occurs.
     * @throws IOException if an error occurs while reading the configuration file.
     */
    public static Configuration getConfiguration() {
        try (FileReader reader = new FileReader(configFile)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.out.println("Failed to load the configuration: " + e.getMessage());
            ;
            return null;
        }
    }

    /**
     * Saves transaction logs to a file, including details about the ticket, transaction type, and thread information.
     *
     * <p>This method generates a log entry with a timestamp, transaction type (e.g., ticket purchase or retrieval),
     * ticket ID, the current thread's name, and the current size of the ticket pool. The log entry is then written
     * to a log file specified by {@code loggerFile}. If successful, the log is saved, and a success message is printed.</p>
     *
     * @param transactionType the type of transaction (e.g., "Buy" or "Retrieve").
     * @param ticket          the {@code Ticket} involved in the transaction.
     * @param tickets         the list of tickets currently in the pool, used to report the current size of the pool.
     * @throws IOException if an error occurs while writing to the log file.
     */
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

