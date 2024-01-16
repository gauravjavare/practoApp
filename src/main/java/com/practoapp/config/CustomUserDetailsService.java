package com.practoapp.config;

import com.practoapp.entity.Doctor;
import com.practoapp.entity.Patient;
import com.practoapp.entity.Admin;
import com.practoapp.repository.DoctorRepository;
import com.practoapp.repository.PatientRepository;
import com.practoapp.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = doctorRepo.findByEmail(username).orElse(null);
        if (doctor != null) {
            return new CustomUserDetails(doctor);
        }

        Patient patient = patientRepo.findByEmail(username).orElse(null);
        if (patient != null) {
            return new CustomUserDetails(patient);
        }

        Admin admin = adminRepo.findByAdminEmail(username).orElse(null);
        if (admin != null) {
            return new CustomUserDetails(admin);
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}
