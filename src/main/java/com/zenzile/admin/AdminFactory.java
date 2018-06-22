package com.zenzile.admin;

public class AdminFactory {
    public static  Admin buildAdmin(Admin admin) {
        Admin newAdmin = new Admin();

        newAdmin.setEmail(admin.getEmail());
        newAdmin.setPassword(admin.getPassword());
        newAdmin.setName(admin.getName());
        newAdmin.setSurname(admin.getSurname());
        return newAdmin;
    }
}
