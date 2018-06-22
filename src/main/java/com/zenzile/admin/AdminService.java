package com.zenzile.admin;

import com.zenzile.admin.exception.EmailAlreadyInUseException;
import com.zenzile.admin.exception.MissingEmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class AdminService {
    public static final Logger LOG = LoggerFactory.getLogger(AdminService.class);
    private AdminRepository adminRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createAdmin(Admin admin) throws MissingEmailException, EmailAlreadyInUseException {
        boolean validAdmin = validateObject(admin);
        if(validAdmin ) {
            if (isEmailInUse(admin.getEmail())) {
                LOG.warn("Email address: {} already in use", admin.getEmail());
                throw new EmailAlreadyInUseException("Email address already in use");
            } else {
                String password = passwordEncoder.encode(admin.getPassword());
                admin.setPassword(password);
                adminRepository.save(admin);
                LOG.info("createAdmin > email address={}", admin.getEmail());
            }
        } else {
            LOG.error("createAdmin > missing admin email time: {}", new Date());
            throw new MissingEmailException("Missing admin email address");
        }
    }

    private boolean isEmailInUse(String email) {
        return adminRepository.findById(email).isPresent();
    }

    public List<Admin> findAdminByName(String name) {
        List<Admin> admins = adminRepository.findAdminByName(name);

        if (admins == null)
            return new ArrayList<>();
        else if(name.equals(""))
            return adminRepository.findAll();
        return admins;

    }

    public boolean deleteAdmin(String email) {
        Optional<Admin> adminToDelete = adminRepository.findById(email);

        if (adminToDelete.isPresent()) {
            adminRepository.delete(adminToDelete.get());
            return true;
        }
        return false;
    }

    private boolean validateObject(Admin admin) {
        if (admin == null)
            return false;
        if(admin.getEmail() == null)
            return false;
        if(admin.getEmail().equals(""))
            return false;
        return true;
    }
}