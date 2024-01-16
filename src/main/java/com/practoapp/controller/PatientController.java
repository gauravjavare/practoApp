package com.practoapp.controller;

import com.practoapp.entity.Doctor;
import com.practoapp.entity.Patient;
import com.practoapp.payload.DoctorWithTimeSlotsDto;
import com.practoapp.service.DoctorService;
import com.practoapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @GetMapping("/search")
    public List<Doctor> searchDoctors(@RequestParam String search) {
        return doctorService.searchByNameOrSpecialization(search);
    }
    @GetMapping("/timeDetails/{doctorId}")
    public DoctorWithTimeSlotsDto getDoctorDetailsWithAvailableTimeSlots(@PathVariable long doctorId) {
        return doctorService.getDoctorDetailsWithAvailableTimeSlots(doctorId);
    }
}
