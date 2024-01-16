package com.practoapp.service.impl;

import com.practoapp.entity.Booking;
import com.practoapp.entity.Doctor;
import com.practoapp.entity.Patient;
import com.practoapp.exception.ResourceNotFoundException;
import com.practoapp.payload.TimeSlot;
import com.practoapp.repository.BookingRepository;
import com.practoapp.repository.DoctorRepository;
import com.practoapp.repository.PatientRepository;
import com.practoapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private PatientRepository patientRepo;

    private List<TimeSlot> timeSlots = initializeTimeSlots();
    // private List<Booking> bookings = new ArrayList<>();

    private List<TimeSlot> initializeTimeSlots() {
        List<TimeSlot> timeSlots = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.of(2023, 11, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 11, 1, 18, 0);

        while (startTime.isBefore(endTime)) {
            timeSlots.add(new TimeSlot(startTime, startTime.plusHours(1)));
            startTime = startTime.plusHours(1);
        }
        return timeSlots;
    }

    @Override
    public Booking saveAppointment(Booking booking) {
        Doctor doctor = doctorRepo.findById(booking.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with given Id " + booking.getDoctorId()));
        Patient patient = patientRepo.findById(booking.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with given id " + booking.getPatientId()));
        if (isBookingValid(booking, doctor)) {
            if (!isDoctorTimeslotBooked(doctor, booking.getBookingDateTime())) {
                booking.setAvailable(true);
                Booking savedBooking = bookingRepo.save(booking);
                return savedBooking;
            } else {
                throw new IllegalArgumentException("Invalid booking: The time slot is not available for the next 24 hours.");
            }
        } else {
            throw new ResourceNotFoundException("Invalid booking: The time slot is not available " + booking.getBookingDateTime());
        }
    }

    private boolean isDoctorTimeslotBooked(Doctor doctor, LocalDateTime bookingDateTime) {
        List<TimeSlot> bookedTimeSlots = bookingRepo.findBookedTimeSlotsForDoctor(doctor.getId());
        if (bookedTimeSlots != null) {
            for (TimeSlot timeSlot : bookedTimeSlots) {
                // Check if the booking time is within 24 hours of any booked timeslot
                if (bookingDateTime.isAfter(timeSlot.getStartTime()) && bookingDateTime.isBefore(timeSlot.getEndTime().plusHours(24))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBookingValid(Booking booking, Doctor doctor) {
        for (TimeSlot timeSlot : timeSlots) {
            if (booking.getBookingDateTime().isEqual(timeSlot.getStartTime()) ||
                    (booking.getBookingDateTime().isAfter(timeSlot.getStartTime()) &&
                            booking.getBookingDateTime().isBefore(timeSlot.getEndTime()))) {
                return isTimeSlotAvailable(timeSlot, doctor);
            }
        }
        return false;
    }

    private boolean isTimeSlotAvailable(TimeSlot timeSlot, Doctor doctor) {
        LocalDateTime startTime = timeSlot.getStartTime();
        LocalDateTime endTime = timeSlot.getEndTime();
        List<TimeSlot> bookedTimeSlots = fetchDoctorBookedTimeSlotsFromDatabase(doctor);
        if (bookedTimeSlots != null) {
            for (TimeSlot bookedSlot : bookedTimeSlots) {
                if (startTime.isBefore(bookedSlot.getEndTime()) && endTime.isAfter(bookedSlot.getStartTime())) {
                    return false; // Timeslot overlaps with a booked timeslot
                } else if (startTime.isEqual(bookedSlot.getStartTime()) || endTime.isEqual(bookedSlot.getEndTime())) {
                    return false; // Timeslot starts or ends exactly at the same time as a booked timeslot
                }
            }
        }
        return true;
    }

    private List<TimeSlot> fetchDoctorBookedTimeSlotsFromDatabase(Doctor doctor) {
        // Fetch booked time slots from the BookingRepository for the specified doctor
        List<Booking> bookedBookings = bookingRepo.findByDoctorId(doctor.getId());
        List<TimeSlot> timeSlots = new ArrayList<>();
        for (Booking booking : bookedBookings) {
            timeSlots.add(new TimeSlot(booking.getBookingDateTime(), booking.getBookingDateTime().plusHours(1)));
        }
        return timeSlots;
    }

    @Override
    public List<TimeSlot> getAvailableTimeSlotsForDoctor(long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with given Id " + doctorId));
        List<TimeSlot> availableTimeSlots = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots) {
            if (isTimeSlotAvailable(timeSlot, doctor) && isDoctorAvailable(doctor, timeSlot)) {
                availableTimeSlots.add(timeSlot);
            }
        }
        List<TimeSlot> bookedTimeSlots = bookingRepo.findBookedTimeSlotsForDoctor(doctorId);
        if (bookedTimeSlots != null) {
            // Filter out any available timeslots that overlap with booked timeslots
            availableTimeSlots = availableTimeSlots.stream()
                    .filter(timeSlot -> bookedTimeSlots.stream()
                            .noneMatch(bookedSlot -> timeSlot.getStartTime().isBefore(bookedSlot.getEndTime()) &&
                                    timeSlot.getEndTime().isAfter(bookedSlot.getStartTime())))
                    .collect(Collectors.toList());
        }

        return availableTimeSlots;
    }



    private boolean isDoctorAvailable(Doctor doctor, TimeSlot timeSlot) {
        LocalDateTime startTime = timeSlot.getStartTime();
        LocalDateTime endTime = timeSlot.getEndTime();
        List<TimeSlot> bookedTimeSlots = bookingRepo.findBookedTimeSlotsForDoctor(doctor.getId());
        if (bookedTimeSlots != null) {
            for (TimeSlot bookedSlot : bookedTimeSlots) {
                if (startTime.isBefore(bookedSlot.getEndTime()) && endTime.isAfter(bookedSlot.getStartTime())) {
                    return false; // Timeslot overlaps with a booked timeslot
                }
            }
        }
        return true;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public List<Booking> getBookingsByDoctorId(long doctorId) {
        return bookingRepo.findByDoctorId(doctorId);
    }


}


