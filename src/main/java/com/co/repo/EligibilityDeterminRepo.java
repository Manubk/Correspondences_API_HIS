package com.co.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.co.entity.EligibilityDeterminEntity;

@Repository
public interface EligibilityDeterminRepo  extends JpaRepository<EligibilityDeterminEntity, Long>{

	public EligibilityDeterminEntity findByCaseNum(Long caseNum);
}
