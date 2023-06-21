package com.co.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.co.entity.CoTriggers;
import java.util.List;


@Repository 
public interface CoTriggersRepo extends JpaRepository<CoTriggers, Long>{
	
	List<CoTriggers> findByStatus(String status);
}
