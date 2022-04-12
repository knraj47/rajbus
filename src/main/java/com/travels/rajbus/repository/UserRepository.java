package com.travels.rajbus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travels.rajbus.entity.User;

public interface UserRepository  extends JpaRepository<User,Long>{

}
