package com.zenzile.login;

import com.zenzile.admin.Admin;
import com.zenzile.admin.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginService {
    private AdminRepository adminRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void logout() {

    }

    public  boolean matchUserCredentials(String password, String email) {
        Optional<Admin> admin = adminRepository.findById(email);

        return admin.isPresent() && passwordEncoder.matches(password, admin.get().getPassword());
    }
}
