package com.practoapp.service;

import com.practoapp.entity.Patient;
import com.practoapp.payload.SignUpDto;

import java.util.List;

public interface PatientService {

    Patient savePatient(SignUpDto signUpDto);

    List<Patient> getAllPatients();
    }

