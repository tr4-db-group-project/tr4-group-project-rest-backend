package com.tr4.db.restapi.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record Booking(
        @NotNull(message = "Event Id cannot be null; must be UUID") UUID eventId,
        @NotNull(message = "Event name cannot be null") String eventName,
        @NotNull(message = "Number of tickets cannot be null") @Min(value = 1, message = "At least one ticket must be booked") Integer numberOfTickets,
        @NotNull(message = "Email cannot be null") @Email(message = "Email must be valid") String email) {
}
