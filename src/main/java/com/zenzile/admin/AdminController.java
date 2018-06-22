package com.zenzile.admin;

import com.zenzile.admin.exception.EmailAlreadyInUseException;
import com.zenzile.admin.exception.MissingEmailException;
import com.zenzile.login.exception.IncorrectCredentialsException;
import com.zenzile.util.ExceptionObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;


    @Autowired
    private ExceptionObject exceptionObject;

    public static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAdmin(@RequestBody Admin admin) {
        adminService.createAdmin(admin);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Admin> findAll() {
        LOG.info("findAll > retrieve all admin");
        return adminRepository.findAll();
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Admin> findAdminByName(@PathVariable String name) {
        return adminService.findAdminByName(name);
    }

    @DeleteMapping("/{email}")
    @ResponseBody
    public HttpStatus deleteAdmin(@PathVariable String email) {
        if(adminService.deleteAdmin(email)) {
            LOG.info("deleteAdmin > {} has been deleted", email);
            return HttpStatus.OK;
        }
        LOG.info("deleteAdmin > {} has NOT been deleted", email);
        return HttpStatus.BAD_REQUEST;
    }

    @ExceptionHandler({ MissingEmailException.class })
    @ResponseBody
    public ExceptionObject handleException(MissingEmailException e) {
        exceptionObject.setErrorCode(HttpStatus.BAD_REQUEST.value());
        exceptionObject.setErrorMessage(e.getMessage());

        return exceptionObject;
    }

    @ExceptionHandler(value = IncorrectCredentialsException.class)
    @ResponseBody
    public ExceptionObject handleException(IncorrectCredentialsException e) {
        exceptionObject.setErrorCode(HttpStatus.FORBIDDEN.value());
        exceptionObject.setErrorMessage(e.getMessage());

        return exceptionObject;
    }

    @ExceptionHandler(value = EmailAlreadyInUseException.class)
    @ResponseBody
    public ExceptionObject handleException(EmailAlreadyInUseException e) {
        exceptionObject.setErrorCode(HttpStatus.FORBIDDEN.value());
        exceptionObject.setErrorMessage(e.getMessage());

        return exceptionObject;
    }

}
