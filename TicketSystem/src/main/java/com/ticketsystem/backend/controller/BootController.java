package com.ticketsystem.backend.controller;

import com.ticketsystem.backend.model.ApiResponse;
import com.ticketsystem.backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
public class BootController {
    @Autowired
    TicketService ticketService;

    @PostMapping("/stop")
    public ResponseEntity<ApiResponse> stopSystem() {
        ticketService.stopSimulation();
        return ResponseEntity.ok(new ApiResponse("System stopped successfully!", true));
    }
}
