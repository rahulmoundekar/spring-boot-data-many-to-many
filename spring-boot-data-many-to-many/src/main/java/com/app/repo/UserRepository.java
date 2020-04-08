package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
