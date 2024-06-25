package com.tr4.db.restapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


public record Booking (UUID eventId, String eventName, Integer numberOfTickets, String email){}
