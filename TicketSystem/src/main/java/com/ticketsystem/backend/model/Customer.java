package com.ticketsystem.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Customer {
    private TicketPool ticketPool;
    private int customerRetrievalRate;
    private int quantity;
}

