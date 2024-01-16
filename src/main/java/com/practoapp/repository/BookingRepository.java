package com.practoapp.repository;

import com.practoapp.entity.Booking;
import com.practoapp.payload.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Define a custom query method to find bookings by date and time
        @Query("SELECT b FROM Booking b WHERE b.bookingDateTime IN :timeSlots")
        List<Booking> findByBookingDateTimeIn(@Param("timeSlots") List<Date> timeSlots);

    // Custom method to fetch booked time slots for a doctor
       @Query("SELECT b.bookingDateTime FROM Booking b WHERE b.doctorId = :doctorId AND b.isAvailable = false")
       List<TimeSlot> findBookedTimeSlotsForDoctor(@Param("doctorId") long doctorId);
        List<Booking> findByDoctorId(Long doctorId);
}




