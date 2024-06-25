package com.tr4.db.restapi.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr4.db.restapi.RestapiApplication;
import com.tr4.db.restapi.domain.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private RestapiApplication.PubsubOutboundGateway messagingGateway;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public String get() {
        return "Hello World";
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public String Publish(@RequestBody Booking booking) throws JsonProcessingException {
        log.info("Booking received: {}", booking);
        messagingGateway.sendToPubsub(objectMapper.writeValueAsString(booking));
        return "Success";
    }
}
