package com.co.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.aspectj.apache.bcel.generic.FieldInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.co.constants.AppConstants;
import com.co.dto.MailRequirments;
import com.co.entity.ApplicationRegistration;
import com.co.entity.EligibilityDeterminEntity;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class Mail {
	
	
	private static final Logger log = LoggerFactory.getLogger(Mail.class);

	
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean createMail(EligibilityDeterminEntity eligibility , ApplicationRegistration appReg,ByteArrayInputStream notice) throws IOException  {
		log.info("createMail");
		
		try {
			String to = appReg.getEmail();
			
			String subject = AppConstants.NOTICE_SUB+" "+eligibility.getPlanName();
			
			String body = "Dear "+appReg.getFullName()
				+"\n This is "+AppConstants.NEW_JERSEY+" state Government Resentely you have appalyed for our scheme "
				+eligibility.getPlanName().toUpperCase()+" All the details given in the pdf kindly go throug it "
				+"\n Thank you "
				+"\n"+AppConstants.NEW_JERSEY;
			
			
			MailRequirments mailRequirments = new MailRequirments();
			mailRequirments.setFrom(to);
			mailRequirments.setBody(body);
			mailRequirments.setSubject(subject);
			
			if(notice != null)
			mailRequirments.setNotice(notice);
			
			
			return sendMail(mailRequirments);
			
		} catch (AddressException a ) {
			log.error("createMail mail = "+appReg.getEmail());
			a.printStackTrace();
		} catch (Exception e) {
			log.error("createMail mail = "+appReg.getEmail());
			e.printStackTrace();
		}finally {
			notice.close();
		}
		return false;
		
	
	}
	
	public boolean sendMail(MailRequirments requirments) throws AddressException{
		log.info("sendMail to = "+requirments.getTo());
		
		MimeMessage mail = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mail);
		try {
			helper.setTo(requirments.getTo());
			helper.setFrom(AppConstants.FROM_MAIL);
			helper.setSubject(requirments.getSubject());
			helper.setText(requirments.getBody());
			
			
//			helper.addAttachment("123.pdf",);
			mailSender.send(mail);
			
			return true;
		} catch (MessagingException e) {
			log.error("sendMail mail = "+requirments.getTo());
			e.printStackTrace();
			return false;
		}

		
	}
}
