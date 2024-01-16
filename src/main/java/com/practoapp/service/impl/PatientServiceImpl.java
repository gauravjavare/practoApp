package com.practoapp.service.impl;

import com.practoapp.entity.Patient;
import com.practoapp.payload.SignUpDto;
import com.practoapp.repository.PatientRepository;
import com.practoapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Patient savePatient(SignUpDto signUpDto) {
        Patient patient = new Patient();
        patient.setName(signUpDto.getName());
        patient.setAge(signUpDto.getAge());
        patient.setEmail(signUpDto.getEmail());
        patient.setDisease(signUpDto.getDisease());
        patient.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        patient.setRole("ROLE_PATIENT");
        return patientRepo.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }
}
