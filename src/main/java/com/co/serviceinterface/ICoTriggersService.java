package com.co.serviceinterface;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.co.dto.MailSentReport;
import com.co.entity.ApplicationRegistration;
import com.co.entity.EligibilityDeterminEntity;

@Service
public interface ICoTriggersService {

	 
	public MailSentReport sendNoticeForPending();
}
