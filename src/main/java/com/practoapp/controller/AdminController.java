package com.practoapp.controller;

import com.practoapp.entity.Admin;
import com.practoapp.entity.Booking;
import com.practoapp.entity.Doctor;
import com.practoapp.entity.Patient;
import com.practoapp.repository.AdminRepository;
import com.practoapp.service.AdminService;
import com.practoapp.service.BookingService;
import com.practoapp.service.DoctorService;
import com.practoapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private AdminService adminService;
    @PostMapping("/addAdmin")
    private ResponseEntity<Admin> addDoctor(@RequestBody Admin admin){
        Admin registerAdmin = adminService.registerAdmin(admin);
        return new ResponseEntity<>(registerAdmin, HttpStatus.CREATED);
    }
    @PostMapping("/addDoctor")
    private ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor){
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }
    @GetMapping("/getAllDoctors")
    private ResponseEntity<List<Doctor>> getAllDoctors(){
        List<Doctor> allDoctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(allDoctors, HttpStatus.OK);
    }
    @GetMapping("/{doctorId}")
    public Doctor getDoctorById(@PathVariable long doctorId) {

        return doctorService.getDoctorById(doctorId);
    }
    @DeleteMapping("/{doctorId}")
    public ResponseEntity<String> deleteDoctorById(@PathVariable long doctorId){
        doctorService.deleteDoctorById(doctorId);
        return new ResponseEntity<>("doctor is deleted",HttpStatus.OK);
    }
    @GetMapping("/getAllPatients")
    public ResponseEntity<List<Patient>> getAllPatients(){
        List<Patient> allPatients = patientService.getAllPatients();
        return new ResponseEntity<>(allPatients,HttpStatus.OK);
    }

    @GetMapping("/getAllBookings")
    public ResponseEntity<List<Booking>> getAllBookings(){
        List<Booking> allBookings = bookingService.getAllBookings();
        return new ResponseEntity<>(allBookings,HttpStatus.OK);
    }
}
