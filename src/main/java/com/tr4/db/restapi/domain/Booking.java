package com.tr4.db.restapi.domain;

import jakarta.validation.constraints.*;


import java.util.UUID;

public record Booking(
        @NotNull(message = "Event Id cannot be null; must be UUID") UUID eventId,
        @NotBlank(message = "Event name cannot be null") String eventName,
        @NotNull(message = "Number of tickets cannot be null") @Min(value = 1, message = "At least one ticket must be booked") Integer numberOfTickets,
        @NotEmpty(message = "Email cannot be null") @Email(message = "Email must be valid") String email) {
}
