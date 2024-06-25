package com.tr4.db.restapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr4.db.restapi.RestapiApplication;
import com.tr4.db.restapi.domain.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class BookingController {
    @Autowired
    private RestapiApplication.PubsubOutboundGateway messagingGateway;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public String get() {
        return "Hello World";
    }

    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.CREATED)
    public String Publish(@RequestBody Booking booking) throws JsonProcessingException {
        messagingGateway.sendToPubsub(objectMapper.writeValueAsString(booking));
        return "Success";
    }
}