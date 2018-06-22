package com.zenzile.admin;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdminRepository extends  MongoRepository<Admin, String> {
    List<Admin> findAdminByName(String name);
}