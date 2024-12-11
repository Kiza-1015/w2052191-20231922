package com.ticketsystem.backend.model;

import lombok.Data;

@Data
public class TicketRequest {
    private int ticketPoolMaxCapacity;
    private int totalTickets;
    private int releaseRate;
    private int retrievalRate;
    private int quantity;
}
