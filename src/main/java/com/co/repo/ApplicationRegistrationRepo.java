package com.co.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.co.entity.ApplicationRegistration;
import java.util.List;


@Repository
public interface ApplicationRegistrationRepo extends JpaRepository<ApplicationRegistration, Long> {
	
	ApplicationRegistration  findByAppId(Long appId);
}
