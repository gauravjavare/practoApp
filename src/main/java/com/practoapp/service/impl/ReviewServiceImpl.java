package com.practoapp.service.impl;

import com.practoapp.entity.Doctor;
import com.practoapp.entity.Patient;
import com.practoapp.entity.Review;
import com.practoapp.exception.ResourceNotFoundException;
import com.practoapp.repository.DoctorRepository;
import com.practoapp.repository.PatientRepository;
import com.practoapp.repository.ReviewRepository;
import com.practoapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepos;
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private PatientRepository patientRepo;
    @Override
    public Review createreview(Review review) {
        Doctor doctor = doctorRepo.findById(review.getDoctorId()).orElseThrow(() ->
                new ResourceNotFoundException("Doctor not found with given id " + review.getDoctorId()));
        Patient patient = patientRepo.findById(review.getPatientId()).orElseThrow(() ->
                new ResourceNotFoundException("Patient not found with given id " + review.getPatientId()));
        Review saveReview = null;
        if(doctor!= null || patient!= null){
            saveReview = reviewRepos.save(review);
        }
        return saveReview;
    }
}
