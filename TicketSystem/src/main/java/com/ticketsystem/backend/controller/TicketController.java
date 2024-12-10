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

    /**
     * call first,third,forth and fifth inputs
     * http://localhost:8080/api/tickets/validate?maxCapacity=50&ticketPoolCapacity=100
    * */
    /*@GetMapping("/validateUserInput")
    public ResponseEntity<TicketResponse> validateUserInput(@RequestParam Integer ticketPoolCapacity) {
        return ResponseEntity.ok(util.checkInput(ticketPoolCapacity));
    }

    *//**
     * call second user input
     * *//*
    @GetMapping("/validateMaxCapacityAndTotalCapacity")
    public ResponseEntity<TicketResponse> validateMaxCapacityAndTotalCapacity(@RequestParam Integer maxCapacity, @RequestParam Integer ticketPoolCapacity) {
        return ResponseEntity.ok(util.checkTotalTicketValidity(maxCapacity, ticketPoolCapacity));
    }*/

   @PostMapping("/processEvent")
   public ResponseEntity<TicketResponse> processEvent(@RequestBody TicketRequest
                                                      ticketRequest) {

       if(ticketRequest.getTicketPoolMaxCapacity() <= 0){
           TicketResponse errorResponse = new TicketResponse("", "Maximum Capacity must be positive non zero value.");
           return ResponseEntity.badRequest().body(errorResponse);
       }
       if(ticketRequest.getTotalTickets() <= 0){
           TicketResponse errorResponse = new TicketResponse("", "Total number of tickets must be positive non zero value.");
           /*if(ticketRequest.getTotalTickets() > ticketRequest.getTicketPoolMaxCapacity()){
               TicketResponse comparisonMessage = new TicketResponse("", "Total tickets can't exceed the maximum ticket capacity");
               return ResponseEntity.badRequest().body(comparisonMessage);
           }*/
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