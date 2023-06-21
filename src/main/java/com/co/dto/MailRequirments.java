package com.co.dto;

import java.io.ByteArrayInputStream;
import java.io.File;

import lombok.Data;

@Data
public class MailRequirments {
	
	private String from;
	private String to;
	private String cc;
	private String bc;
	private String subject;
	private String body;
	private ByteArrayInputStream notice;
	private File file ;
}
