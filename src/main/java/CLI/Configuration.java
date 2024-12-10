package CLI;

import java.io.*;
import com.google.gson.*;

public class Configuration {
    private final int totalTickets;
    private final int maximumCapacity;
    private final int releaseRate;
    private final int retrievalRate;
    private final int quantity;

    /**
     * Constructs a {@code Configuration} object with specified values for the ticketing system.
     *
     * <p>This constructor initializes the configuration with values for total tickets,
     * maximum capacity, release rate, retrieval rate, and the quantity of tickets a
     * customer can purchase in a single transaction.</p>
     *
     * @param totalTickets the total number of tickets available in the system.
     * @param maximumCapacity the maximum capacity of the ticket pool.
     * @param releaseRate the rate at which tickets are released into the pool.
     * @param retrievalRate the rate at which tickets are retrieved from the pool.
     * @param quantity the quantity of tickets that a customer can buy in a single transaction.
     */
    public Configuration(int totalTickets, int maximumCapacity, int releaseRate, int retrievalRate, int quantity) {
        this.totalTickets = totalTickets;
        this.maximumCapacity = maximumCapacity;
        this.releaseRate = releaseRate;
        this.retrievalRate = retrievalRate;
        this.quantity = quantity;

    }

    /**
     * Retrieves the total number of tickets available in the configuration.
     *
     * @return the total number of tickets.
     */
    public int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Retrieves the maximum capacity of the ticket pool.
     *
     * @return the maximum capacity of the ticket pool.
     */
    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    /**
     * Retrieves the rate at which tickets are released into the ticket pool.
     *
     * @return the release rate of tickets.
     */
    public int getReleaseRate() {
        return releaseRate;
    }

    /**
     * Retrieves the rate at which tickets are retrieved from the ticket pool.
     *
     * @return the retrieval rate of tickets.
     */
    public int getRetrievalRate() {
        return retrievalRate;
    }

    /**
     * Retrieves the quantity of tickets that a customer can buy in a single transaction.
     *
     * @return the quantity of tickets per customer purchase.
     */
    public int getQuantity() {
        return quantity;
    }

}
