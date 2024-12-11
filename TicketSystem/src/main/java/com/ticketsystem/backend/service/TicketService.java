package com.ticketsystem.backend.service;

import com.ticketsystem.backend.model.TicketRequest;
import com.ticketsystem.backend.model.TicketResponse;

public interface TicketService {
    TicketResponse processEvent(TicketRequest ticketRequest);
    void stopSimulation();
}
