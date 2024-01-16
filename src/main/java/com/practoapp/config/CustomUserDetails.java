package com.practoapp.config;

import com.practoapp.entity.Doctor;
import com.practoapp.entity.Patient;
import com.practoapp.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private Object user; // Use a common type for all three entities

    public CustomUserDetails(Object user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user instanceof Doctor) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(((Doctor) user).getRole());
            return Collections.singleton(simpleGrantedAuthority);
        } else if (user instanceof Patient) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(((Patient) user).getRole());
            return Collections.singleton(simpleGrantedAuthority);
        } else if (user instanceof Admin) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(((Admin) user).getRole());
            return Collections.singleton(simpleGrantedAuthority);
        }

        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        if (user instanceof Doctor) {
            return ((Doctor) user).getPassword();
        } else if (user instanceof Patient) {
            return ((Patient) user).getPassword();
        } else if (user instanceof Admin) {
            return ((Admin) user).getAdminPassword();
        }

        return null;
    }

    @Override
    public String getUsername() {
        if (user instanceof Doctor) {
            return ((Doctor) user).getEmail();
        } else if (user instanceof Patient) {
            return ((Patient) user).getEmail();
        } else if (user instanceof Admin) {
            return ((Admin) user).getAdminEmail();
        }

        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
