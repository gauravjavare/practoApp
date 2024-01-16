package com.practoapp.controller;


import com.practoapp.entity.Booking;
import com.practoapp.payload.DoctorWithTimeSlotsDto;
import com.practoapp.service.BookingService;
import com.practoapp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private  DoctorService doctorService;
    @Autowired
    private BookingService bookingService;
    @GetMapping("/timeDetails/{doctorId}")
    public DoctorWithTimeSlotsDto getDoctorDetailsWithAvailableTimeSlots(@PathVariable long doctorId) {
        return doctorService.getDoctorDetailsWithAvailableTimeSlots(doctorId);
    }
    @GetMapping("/getAllBookingsByDoctorId/{doctorId}")
    public ResponseEntity<List<Booking>> getBookingByDoctorId(@PathVariable long doctorId){
        List<Booking> bookingsByDoctorId = bookingService.getBookingsByDoctorId(doctorId);
        return new ResponseEntity<>(bookingsByDoctorId, HttpStatus.OK);
    }


}

