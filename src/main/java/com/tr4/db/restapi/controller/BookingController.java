package com.tr4.db.restapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr4.db.restapi.RestapiApplication.PubsubOutboundGateway;
import com.tr4.db.restapi.domain.Booking;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private PubsubOutboundGateway messagingGateway;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("")
    public ResponseEntity<String> publish(@Valid @RequestBody Booking booking) {
        try {
            log.info("Booking received: {}", booking);
            messagingGateway.sendToPubsub(objectMapper.writeValueAsString(booking));
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            log.error("Error processing booking: {}", booking, e);
            return new ResponseEntity<>("Failed to process booking", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
