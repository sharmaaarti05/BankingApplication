package com.ba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ba.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	public Admin findByUsernameAndPassword(String name, String location);
}
