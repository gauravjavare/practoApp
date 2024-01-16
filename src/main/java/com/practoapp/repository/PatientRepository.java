package com.practoapp.repository;

import com.practoapp.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Boolean existsByEmail(String email);
    Optional<Patient>findByEmail(String username);
}
