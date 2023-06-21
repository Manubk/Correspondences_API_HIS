package com.co.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ED_TABLE")
public class EligibilityDeterminEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ELD_ID")
	private Long eligDeterminId;
	
	@Column(name = "CASE_NUM")
	private Long caseNum;
	
	@Column(name = "PLAN_NAME")
	private String planName;
	
	@Column(name = "STATUS")
	private String planStatus;
	
	@Column(name = "PLAN_START_DATE")
	private LocalDate planStartDate;
	
	@Column(name = "PLAN_END_DATE")
	private LocalDate planEndDate;
	
	@Column(name = "BENEFIT_AMOUNT")
	private Double benefitAmount;
	
	@Column(name = "DENIAL_REASON")
	private String denialReason;
}
