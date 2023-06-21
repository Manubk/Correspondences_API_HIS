package com.co.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.co.constants.AppConstants;
import com.co.dto.MailSentReport;
import com.co.entity.ApplicationRegistration;
import com.co.entity.CoTriggers;
import com.co.repo.CoTriggersRepo;
import com.co.repo.EligibilityDeterminRepo;
import com.co.serviceinterface.ICoTriggersService;
import com.co.util.Mail;
import com.co.entity.DcCase;
import com.co.repo.DcCaseRepo;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.lowagie.text.Document;
import com.lowagie.text.Header;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import ch.qos.logback.classic.pattern.Util;

import com.co.entity.EligibilityDeterminEntity;
import com.co.repo.ApplicationRegistrationRepo;

@Service
public class CoTriggersService implements ICoTriggersService {

	private static final Logger log = LoggerFactory.getLogger(CoTriggersService.class);

	@Autowired
	private Mail mail;

	@Autowired
	private CoTriggersRepo triggersRepo;

	@Autowired
	private EligibilityDeterminRepo eligibilityRepo;

	@Autowired
	private ApplicationRegistrationRepo applicationRepo;

	@Autowired
	private DcCaseRepo caseRepo;

	@Autowired
	private MailSender mailSender;

	@Override
	public MailSentReport sendNoticeForPending() {
		log.info("sendNoticeForPending");
		
		Long totalMails = 0l;
		Long sentMails = 0l;
		Long failedMails = 0l;
		try {
			// Get the pending Triggers for CoTriggers
			List<CoTriggers> triggers = triggersRepo.findByStatus("Pending");
			totalMails = Long.valueOf(triggers.size());
			
			// for each triggers should
			for (CoTriggers trigger : triggers) {

				// get Eligibility Details
				EligibilityDeterminEntity eligibility = eligibilityRepo.findByCaseNum(trigger.getCaseNum());

				// should get DcCases for AppId
				DcCase dcCase = caseRepo.findByCaseNum(trigger.getCaseNum());

				ApplicationRegistration appReg = applicationRepo.findByAppId(dcCase.getAppId());

				// create Pdf based on Eligibility and appReg
				ByteArrayInputStream generatedPdf = generatePdf(eligibility, appReg);

				trigger.setPdf(generatedPdf.readAllBytes());


				// should create and send the email to the citizen
				boolean isMailSent = mail.createMail(eligibility, appReg, generatedPdf);
				
				if(isMailSent)
					sentMails++;
				else 
					failedMails++;
			
			}
		} catch (Exception e) {
			log.error("sendNoticeForPending");
		
		} finally {

		}

		// should update the triggert to completed
		MailSentReport report = new MailSentReport();
		report.setTotalMails(totalMails);
		report.setSentMails(sentMails);
		report.setFailedMails(failedMails);
		
		return report;
		
	}

	private ByteArrayInputStream generatePdf(EligibilityDeterminEntity eligibility, ApplicationRegistration appReg) {
		log.info("generatePdf caseNum = " + eligibility.getCaseNum());

		try (ByteArrayOutputStream out = new ByteArrayOutputStream(); Document doc = new Document();) {

			PdfWriter.getInstance(doc, out);
			doc.open();
			
			HeaderFooter mainHeader = new HeaderFooter(true);

			String details = "Dear " + appReg.getFullName()
					+ " this is new Jersy government as per your new Request for our scheme" + eligibility.getPlanName()
					+ "we would like to inform you that after all the document varification";

			if (eligibility.getPlanStatus().equalsIgnoreCase("approved"))
				details = details + " you are eligbal for this scheme Details is as below /n" + "Name : "
						+ appReg.getFullName() + "/n Plan Name : " + eligibility.getPlanName() + "/n Plan Status : "
						+ eligibility.getPlanStatus() + "/n Plan StartDate : " + eligibility.getPlanStartDate()
						+ "/n Plan EndDate : " + eligibility.getPlanEndDate() + "/n Benifit Amount : "
						+ eligibility.getBenefitAmount();
			else
				details = details + "We Regret to inform you that you are not eligible to this scheme" + "Reason : "
						+ eligibility.getDenialReason() + " Kindly contact DIS office for more information";

			Header header = new Header("gov", "New Jerssy HIS Notice");

			Paragraph parDetails = new Paragraph(details);

			doc.add(header);
			doc.add(parDetails);
			
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
