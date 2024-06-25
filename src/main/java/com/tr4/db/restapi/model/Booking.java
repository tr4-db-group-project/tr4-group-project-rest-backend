package com.tr4.db.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Booking {
    private UUID eventId;
    private String eventName;
    private Integer numberOfTickets;
    private String email;
}
