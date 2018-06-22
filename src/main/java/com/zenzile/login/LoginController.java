package com.zenzile.login;

import com.zenzile.admin.Admin;
import com.zenzile.admin.AdminRepository;
import com.zenzile.login.exception.IncorrectCredentialsException;
import com.zenzile.util.ExceptionObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    public static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ExceptionObject exceptionObject;

    @PostMapping
    @ResponseBody
    public Admin login(@RequestBody Login login) {
        String email = (login != null && login.getEmail() != null) ? login.getEmail() : "";
        String password = (login != null && login.getPassword() != null) ? login.getPassword() : "";

        if (loginService.matchUserCredentials(password, email)) {
            LOG.info("Admin: {}, successfully logged in", email);
            return adminRepository.findById(email).get();
        }

        LOG.error("Incorrect admin credentials: password: {} email: {}",  password, email);
        throw new IncorrectCredentialsException("Invalid username and Password");
    }

    @ExceptionHandler(value = IncorrectCredentialsException.class)
    @ResponseBody
    public ExceptionObject handleExpectedContentNotFoundException(IncorrectCredentialsException e) {
        String message = e.getMessage();
        int code = HttpStatus.FORBIDDEN.value();

        exceptionObject.setErrorCode(code);
        exceptionObject.setErrorMessage(message);

        return exceptionObject;
    }
}
