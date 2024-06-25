package com.tr4.db.restapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.tr4.db.restapi.model.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final PubSubTemplate pubSubTemplate;

    @Autowired
    public BookingController(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    @GetMapping("")
    public ResponseEntity<?> getBooking() {
        return ResponseEntity.ok("Received request");
    }

    @PostMapping("")
    public ResponseEntity<Booking> postRequest(@RequestBody Booking booking) {
        log.info("Booking received: {}", booking);

        String jsonMessage = convertBookingToJson(booking);

        // Publish the request body to the Pub/Sub topic
        pubSubTemplate.publish("booking-test", jsonMessage)
                .whenComplete((result, throwable) -> {
                    if (throwable == null) {
                        log.info("Message successfully submitted to Pub/Sub");
                    } else {
                        log.error("Failed to submit message to Pub/Sub", throwable);
                    }
                });

        return ResponseEntity.ok(booking);
    }

    private String convertBookingToJson(Booking booking) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(booking);
        } catch (JsonProcessingException e) {
            log.error("Error converting booking to JSON", e);
            throw new RuntimeException(e);
        }
    }
}
