package com.practoapp.controller;

import com.practoapp.entity.Booking;
import com.practoapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping
    public ResponseEntity<Booking> createAppointment(@RequestBody Booking booking){
        return new ResponseEntity<>(bookingService.saveAppointment(booking), HttpStatus.CREATED);
    }

}
