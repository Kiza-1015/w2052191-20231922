package com.ticketsystem.backend.controller;

import com.ticketsystem.backend.model.TicketRequest;
import com.ticketsystem.backend.model.TicketResponse;
import com.ticketsystem.backend.service.TicketService;
import com.ticketsystem.backend.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/tickets")  // Updated base mapping
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    Util util;

   @PostMapping("/processEvent")
   public ResponseEntity<TicketResponse> processEvent(@RequestBody TicketRequest
                                                      ticketRequest) {

       if(ticketRequest.getTicketPoolMaxCapacity() <= 0){
           TicketResponse errorResponse = new TicketResponse("", "Maximum Capacity must be positive non zero value.");
           return ResponseEntity.badRequest().body(errorResponse);
       }
       if(ticketRequest.getTotalTickets() <= 0 || ticketRequest.getTotalTickets() > ticketRequest.getTicketPoolMaxCapacity()){
           TicketResponse errorResponse = new TicketResponse("", "Total number of tickets must be positive non zero value.");
           return ResponseEntity.badRequest().body(errorResponse);
       }
       if(ticketRequest.getReleaseRate() <= 0){
           TicketResponse errorResponse = new TicketResponse("", "Release Rate must be positive non zero value.");
           return ResponseEntity.badRequest().body(errorResponse);

       }
       if(ticketRequest.getRetrievalRate() <= 0){
           TicketResponse errorResponse = new TicketResponse("", "Retrieval Rate must be positive non zero value.");
           return ResponseEntity.badRequest().body(errorResponse);
       }
       if(ticketRequest.getQuantity() <= 0){
           TicketResponse errorResponse = new TicketResponse("", "Quantity of the tickets that a customer can purchase must be positive non zero value.");
           return ResponseEntity.badRequest().body(errorResponse);
       }
        return ResponseEntity.ok(ticketService.processEvent(ticketRequest));
   }
}