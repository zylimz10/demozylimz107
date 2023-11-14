package com.login.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.login.app.entity.Users;


public interface UserRepository extends JpaRepository<Users, Long> {
	Users findByUsername(String username);
}
