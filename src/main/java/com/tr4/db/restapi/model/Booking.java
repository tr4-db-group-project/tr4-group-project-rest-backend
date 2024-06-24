package com.tr4.db.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Booking {
    private Long id;
    private String eventName;
    private Integer numberOfTickets;
    private String email;
}
