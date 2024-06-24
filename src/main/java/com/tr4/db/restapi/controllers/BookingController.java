package com.tr4.db.restapi.controllers;

import com.tr4.db.restapi.model.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @GetMapping("")
    public ResponseEntity<?> getBooking(){
        return ResponseEntity.ok("Received request");
    }

    @PostMapping("")
    public ResponseEntity<Booking> postRequest(@RequestBody Booking booking) {
        return ResponseEntity.ok(booking);
    }
}

