package com.tr4.db.restapi.domain;

public record Booking(
        String eventid,
        String eventName,
        int numOfTickets,
        String email
) {}
