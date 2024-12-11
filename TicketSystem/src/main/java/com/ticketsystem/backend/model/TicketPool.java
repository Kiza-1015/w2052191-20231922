package com.ticketsystem.backend.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class TicketPool {
    final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());
    private final int maxCapacity;
}
