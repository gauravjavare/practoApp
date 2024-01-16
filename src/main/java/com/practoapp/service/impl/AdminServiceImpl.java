package com.practoapp.service.impl;

import com.practoapp.entity.Admin;
import com.practoapp.repository.AdminRepository;
import com.practoapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Admin registerAdmin(Admin admin) {
        admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));
        admin.setRole("ROLE_ADMIN");
        return adminRepo.save(admin);
    }
}
