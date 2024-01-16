package com.practoapp.repository;

import com.practoapp.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM Doctor d WHERE d.name LIKE %:search% OR d.specializations LIKE %:search%")
    List<Doctor> searchByNameOrSpecialization(@Param("search") String search);

    Optional<Doctor> findByEmail(String email);
    Boolean existsByEmail(String email);



}

