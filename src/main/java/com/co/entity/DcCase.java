package com.co.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "DC_CASES")
public class DcCase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CASE_NUM")
	private Long caseNum;
	
	@Column(name = "APP_ID")
	private Long appId;
	
	@Column(name = "PLAN_ID")
	private Integer planId;
	
	}
