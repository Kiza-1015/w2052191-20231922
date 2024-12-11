package com.ticketsystem.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TicketResponse {
    private String error;
    private String errorMessage;
}
