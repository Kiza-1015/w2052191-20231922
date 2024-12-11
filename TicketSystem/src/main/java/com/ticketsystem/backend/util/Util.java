package com.ticketsystem.backend.util;

import com.ticketsystem.backend.model.Ticket;
import com.ticketsystem.backend.model.TicketResponse;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;

@Component
public class Util {
    private final MessageLogger logger = MessageLogger.getInstance();

    public synchronized void addTicket(List<Ticket> tickets, Ticket ticket, int maxCapacity) {
        while (tickets.size() >= maxCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
        tickets.add(ticket);
        notifyAll();
        logger.log(Thread.currentThread().getName() + ": Ticket added to the pool. Current size is" + tickets.size() + "\n");
        FileManager.saveLogs("TICKET ADDED BY THE VENDOR", ticket, tickets);
    }


    public synchronized Ticket buyTicket(List<Ticket> tickets) {
        while (tickets.isEmpty()) {
            try {
                wait(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        Ticket ticket = tickets.remove(0);
        notifyAll();
        logger.log(Thread.currentThread().getName() + ": Ticket bought! Current size is" + tickets.size() + "\n");
        FileManager.saveLogs("TICKET PURCHASED BY THE CUSTOMER", ticket, tickets);

        return ticket;
    }
}
