package com.ticketsystem.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class Ticket {

    private  int ticketId;
    private  String eventName;
    private  BigDecimal price;

}