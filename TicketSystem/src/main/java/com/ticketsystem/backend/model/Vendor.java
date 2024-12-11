package com.ticketsystem.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Vendor {
    private TicketPool ticketPool;
    private int totalTickets;
    private int ticketReleaseRate;

}
