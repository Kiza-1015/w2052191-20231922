package com.ticketsystem.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/system")
public class BootController {

    private boolean systemRunning = false; // Tracks if the system is running

    @GetMapping("/start")
    public String startSystem() {
        if (systemRunning) {
            return "System is already running!";
        }

        // Add logic to start your system or simulation
        systemRunning = true;

        return "System started successfully!";
    }

    @GetMapping("/stop")
    public String stopSystem() {
        if (!systemRunning) {
            return "System is not running!";
        }

        // Add logic to stop your system or simulation
        systemRunning = false;

        return "System stopped successfully!";
    }

    @GetMapping("/api/system/status")
    public String checkSystemStatus() {
        return systemRunning ? "System is running." : "System is stopped.";
    }
}
