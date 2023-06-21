package com.co.entity;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "CO_TRIGGERS")
public class CoTriggers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRG_ID")
	private Long trgId;
	
	@Column(name = "CASE_NUM")
	private Long caseNum;
	
	@Column(name = "TRG_STATUS")
	private String status;
	
	@Column(name = "NOTICE")
	@Lob
	private byte[] pdf;
	
	
}
