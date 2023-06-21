package com.co.dto;

import lombok.Data;

@Data
public class MailSentReport {
	
	private Long totalMails ;
	private Long sentMails ;
	private Long failedMails;
}
