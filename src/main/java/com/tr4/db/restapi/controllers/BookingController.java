package com.tr4.db.restapi.controllers;

import com.tr4.db.restapi.model.Booking;
import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @GetMapping("")
    public ResponseEntity<?> getBooking(){
        return ResponseEntity.ok("Received request");
    }

    @PostMapping("")
    public ResponseEntity<Booking> postRequest(@RequestBody Booking booking) {
        logger.info("Booking received: {}",booking);
        return ResponseEntity.ok(booking);
    }
}

