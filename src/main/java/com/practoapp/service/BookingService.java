package com.practoapp.service;

import com.practoapp.entity.Booking;
import com.practoapp.payload.TimeSlot;

import java.util.List;

public interface BookingService {
    Booking saveAppointment(Booking booking);
    public List<TimeSlot> getAvailableTimeSlotsForDoctor(long doctorId);
    //public Booking cancelAppointment(long bookingId);
   List<Booking> getAllBookings();
   List<Booking> getBookingsByDoctorId(long doctorId);
}
