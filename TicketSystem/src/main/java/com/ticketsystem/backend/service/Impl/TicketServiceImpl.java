package com.ticketsystem.backend.service.Impl;

import com.ticketsystem.backend.model.*;
import com.ticketsystem.backend.service.TicketService;
import com.ticketsystem.backend.util.FileManager;
import com.ticketsystem.backend.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    Util util;

    @Override
    public TicketResponse processEvent(TicketRequest ticketRequest) {
        try {
            Configuration configuration = new Configuration(ticketRequest.getTotalTickets(), ticketRequest.getTicketPoolMaxCapacity(),
                    ticketRequest.getReleaseRate(), ticketRequest.getRetrievalRate(), ticketRequest.getQuantity());
            FileManager.saveConfiguration(configuration);

            multiThreading(ticketRequest.getTicketPoolMaxCapacity(), ticketRequest.getTicketPoolMaxCapacity(),
                    ticketRequest.getReleaseRate(), ticketRequest.getRetrievalRate(), ticketRequest.getQuantity());
            return new TicketResponse("Success", "");
        } catch (Exception e) {
            log.error("Exception occurred while processing ticket", e);
            return new TicketResponse("Error", e.getMessage());
        }

    }

    public void multiThreading(int maximumCapacity, int totalTickets, int releaseRate, int retrievalRate, int quantity) {
        TicketPool ticketpool = new TicketPool(maximumCapacity);

        Vendor[] vendors = new Vendor[10];
        for (int i = 1; i <= vendors.length; i++) {
            vendors[i - 1] = new Vendor(ticketpool, totalTickets, releaseRate);
            // Thread vendorThread = new Thread(vendors[i - 1], "Vendor - " + i);
            Thread vendorThread = new Thread(() -> {
                for (int j = 1; j <= totalTickets; j++) {
                    Ticket ticket = new Ticket(j, "Event", new BigDecimal(1000));
                    util.addTicket(ticketpool.getTickets(), ticket, maximumCapacity);

                    try {
                        //System.out.println("Enter 'S' to stop the simulation: ");
                        Thread.sleep(releaseRate * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
            });
            vendorThread.start();
        }

        Customer[] customers = new Customer[10];
        for (int i = 1; i <= customers.length; i++) {
            customers[i - 1] = new Customer(ticketpool, retrievalRate, quantity);
            // Thread customerThread = new Thread(customers[i - 1], "Customer - " + i);
            Thread customerThread = new Thread(() -> {
                for (int j = 0; j < quantity; j++) {
                    Ticket ticket = util.buyTicket(ticketpool.getTickets());
                    System.out.println("Ticket bought by " + Thread.currentThread().getName() + ". Ticket is " + ticket.toString());

                    try {
                        Thread.sleep(retrievalRate * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
            });
            customerThread.start();
        }
    }


}