package com.umbra.umbralink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umbra.umbralink.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
