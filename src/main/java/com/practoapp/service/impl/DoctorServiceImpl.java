package com.practoapp.service.impl;

import com.practoapp.entity.Doctor;
import com.practoapp.exception.ResourceNotFoundException;
import com.practoapp.payload.DoctorWithTimeSlotsDto;
import com.practoapp.payload.TimeSlot;
import com.practoapp.repository.DoctorRepository;
import com.practoapp.service.BookingService;
import com.practoapp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private BookingService bookingService;
    @Autowired
   private PasswordEncoder passwordEncoder;


    @Override
    public Doctor saveDoctor(Doctor doctor) {
        doctor.setRole("ROLE_DOCTOR");
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepo.save(doctor);
    }

    @Override
    public Doctor getDoctorById(long doctorId) {

        return doctorRepo.findById(doctorId).get();
    }

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = doctorRepo.findAll();
        return doctors;
    }

    @Override
    public void deleteDoctorById(long doctorId) {
        doctorRepo.deleteById(doctorId);
    }

    @Override
    public List<Doctor> searchByNameOrSpecialization(String search) {
        return doctorRepo.searchByNameOrSpecialization(search);
    }

    @Override
    public DoctorWithTimeSlotsDto getDoctorDetailsWithAvailableTimeSlots(long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with given Id " + doctorId));

        List<TimeSlot> availableTimeSlots = bookingService.getAvailableTimeSlotsForDoctor(doctorId);

        DoctorWithTimeSlotsDto doctorWithTimeSlotsDto = new DoctorWithTimeSlotsDto();
        doctorWithTimeSlotsDto.setDoctorId(doctor.getId());
        doctorWithTimeSlotsDto.setDoctorName(doctor.getName()); // You can add more doctor details as needed
        doctorWithTimeSlotsDto.setAvailableTimeSlots(availableTimeSlots);

        return doctorWithTimeSlotsDto;
    }
}



