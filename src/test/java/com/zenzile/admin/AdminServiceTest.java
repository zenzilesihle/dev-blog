package com.zenzile.admin;

import com.zenzile.admin.exception.EmailAlreadyInUseException;
import com.zenzile.admin.exception.MissingEmailException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String password;

    private Admin admin;

    @Test
    public void create() {
        adminRepository.delete(admin);
        password = admin.getPassword();
        adminService.createAdmin(admin);

        Optional<Admin> savedAdmin = adminRepository.findById(admin.getEmail());

        Assert.assertTrue(savedAdmin.isPresent());
        Assert.assertEquals(savedAdmin.get().getName(), admin.getName());
        Assert.assertEquals(savedAdmin.get().getSurname(), admin.getSurname());
        Assert.assertTrue(passwordEncoder.matches(password, savedAdmin.get().getPassword()));
    }

    @Test
    public void matchPassword() {
        adminRepository.delete(admin);
        password = admin.getPassword();
        adminService.createAdmin(admin);

        Optional<Admin> savedAdmin = adminRepository.findById(admin.getEmail());

        Assert.assertTrue(savedAdmin.isPresent());
        Assert.assertTrue(passwordEncoder.matches(password, savedAdmin.get().getPassword()));
    }

    @Test
    public void findByName() {
        adminRepository.save(admin);

        List<Admin> savedAdmins = adminRepository.findAdminByName(admin.getName());

        Assert.assertTrue(savedAdmins.size() > 0);
    }

    @Test
    public void delete() {
        adminRepository.save(admin);
        Optional<Admin> adminToDelete = adminRepository.findById(admin.getEmail());

        Assert.assertTrue(adminToDelete.isPresent());

        String adminToDeleteEmail = adminToDelete.get().getEmail();
        adminService.deleteAdmin(adminToDeleteEmail);

        Assert.assertFalse(adminRepository.findById(adminToDeleteEmail).isPresent());
    }

    @Before
    public void setUp() {
        admin = new Admin();
        admin.setEmail("sihle@gmail.com");
        admin.setPassword("Test1234");
        admin.setName("Micheal");
        admin.setSurname("Jackson");
    }

    @Test(expected = MissingEmailException.class)
    public void createMissingEmail() {
        Admin admin2 = new Admin();
        admin2.setPassword("Test1234");
        admin2.setName("Micheal");
        admin2.setSurname("Jackson");

        adminService.createAdmin(admin2);
        Optional<Admin> savedAdmin = adminRepository.findById(admin2.getEmail());

        Assert.assertFalse(savedAdmin.isPresent());
    }

    @Test(expected = EmailAlreadyInUseException.class)
    public void createEmailAlreadyInUseException() {
        adminService.createAdmin(admin);
        adminService.createAdmin(admin);
    }
}