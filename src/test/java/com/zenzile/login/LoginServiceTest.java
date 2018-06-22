package com.zenzile.login;

import com.zenzile.admin.Admin;
import com.zenzile.admin.AdminRepository;
import com.zenzile.admin.AdminService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {
    @Autowired
    private LoginService loginService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    private String password;
    private String email;
    private Admin admin;

    @Test
    public void testAdmin() {
//        Admin admin = adminRepository.findById(email).get();
        Login login = new Login();

        login.setEmail(email);
        login.setPassword(password);

        Assert.assertTrue(loginService.matchUserCredentials(password, email));
    }

    @Before
    public void setUp() {
        admin = new Admin();
        admin.setEmail("loginUser@gmail.com");
        admin.setPassword("Login1234");
        admin.setName("Login");
        admin.setSurname("Userout");

        email = admin.getEmail();
        password = admin.getPassword();

        adminRepository.delete(admin);
        adminService.createAdmin(admin);
    }
}