package com.practoapp.service;


import com.practoapp.entity.Doctor;
import com.practoapp.payload.DoctorWithTimeSlotsDto;

import java.util.List;

public interface DoctorService {

    Doctor saveDoctor(Doctor doctor);
    Doctor getDoctorById(long doctorId);

    List<Doctor> getAllDoctors();

    void deleteDoctorById(long doctorId);
    List<Doctor> searchByNameOrSpecialization(String search);
    public DoctorWithTimeSlotsDto getDoctorDetailsWithAvailableTimeSlots(long doctorId);
}
