package com.ticketsystem.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Configuration {

    private int totalTickets;
    private int maximumCapacity;
    private int releaseRate;
    private int retrievalRate;
    private int quantity;

}