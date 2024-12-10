package com.ticketsystem.backend.util;

import com.ticketsystem.backend.model.Ticket;
import com.ticketsystem.backend.model.TicketResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;

@Slf4j
@Component
public class Util {

    public TicketResponse checkInput(Integer number) {
        boolean correct = true;
        while (correct) {
            try {
                if (number > 0) {
                    correct = false;
                } else {
                    log.info("Enter a positive non zero integer");
                    return new TicketResponse("Error", "Enter a positive non zero integer");
                }
            } catch (InputMismatchException e) {
                log.error("Please enter a valid number");
                return new TicketResponse("Error", "Please enter a valid number");
            }
        }
        return new TicketResponse("Success", "");
    }

    public TicketResponse checkTotalTicketValidity(int maximumCapacity, int totalTickets) {

        TicketResponse maximumCapRes = checkInput(maximumCapacity);
        if (maximumCapRes.getError().equalsIgnoreCase("Error"))
            return maximumCapRes;
        TicketResponse totalTicketRes = checkInput(totalTickets);
        if (totalTicketRes.getError().equalsIgnoreCase("Error"))
            return totalTicketRes;

        if (totalTickets > maximumCapacity) {
            log.info("Total tickets can't exceed maximum number of tickets");
            return new TicketResponse("Error", "Total tickets can't exceed maximum number of tickets");
        }
        return new TicketResponse("Success", "");

    }

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
        System.out.println(Thread.currentThread().getName() + ": Ticket added to the pool. Current size is" + tickets.size() + "\n");
        FileManager.saveLogs("TICKET ADDED BY THE VENDOR", ticket, tickets);
      /*  if (tickets.size() == maxCapacity) {

            System.exit(0);
        }*/
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
        System.out.println(Thread.currentThread().getName() + ": Ticket bought! Current size is" + tickets.size() + "\n");
        FileManager.saveLogs("TICKET PURCHASED BY THE CUSTOMER", ticket, tickets);

        return ticket;
    }
}
