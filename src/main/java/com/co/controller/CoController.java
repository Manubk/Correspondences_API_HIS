package com.co.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.result.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import com.co.dto.MailSentReport;
import com.co.service.CoTriggersService;
import com.co.serviceinterface.ICoTriggersService;
import com.lowagie.text.Document;
import com.lowagie.text.Header;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

@CrossOrigin
@RestController
public class CoController {

	@Value("${spring.mail.host}")
	public String host;

	@Value("${spring.mail.port}")
	public int port;

	@Value("${spring.mail.username}")
	public String username;

	@Value("${spring.mail.password}")
	public String password;

	@Autowired
	private JavaMailSender mailsender;
	
	@Autowired
	private ICoTriggersService triggerService;

	@GetMapping("/trigger")
	public ResponseEntity<MailSentReport> trigger(){
		MailSentReport sendNoticeForPending = triggerService.sendNoticeForPending();
		
		return new ResponseEntity<MailSentReport>(sendNoticeForPending,HttpStatus.OK);
	}
	@GetMapping("/mail")
	public ResponseEntity<String> mailSend() {

		try {

			JavaMailSenderImpl mails = new JavaMailSenderImpl();

			mails.setUsername(username);
			mails.setPassword(password);
			mails.setPort(port);
			mails.setHost(host);

			Properties pro = mails.getJavaMailProperties();
			pro.put("mail.smtp.starttls.enable", "true");

			mails.setJavaMailProperties(pro);

			SimpleMailMessage m = new SimpleMailMessage();
			m.setTo("yoyomaltesh4040@gmail.com");
			m.setSubject("hi there");
			m.setFrom(username);
			m.setText("this is maltesh");

			mails.send(m);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("sent", HttpStatus.OK);
	}

	@GetMapping("/mail1")
	public ResponseEntity<String> sendMail1() {

		SimpleMailMessage m = new SimpleMailMessage();

		m.setTo("yoyomaltesh4040@gmail.com");

		m.setFrom("malteshbk1999@gmail.com");

		m.setSubject("testing ");
		m.setText("testing mails ");
		mailsender.send(m);

		return null;
	}

	@GetMapping("/mailf")
	public ResponseEntity<String> sendFilemail() {
		MimeMessage memi = mailsender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(memi, true);
			helper.setTo("yoyomaltesh4040@gmail.com");
			helper.setFrom("malteshbk1999@gmail.com");
			helper.setSubject("sending a resume ");
			helper.setText("hi theere here is my resume");
			File file = new File("C:\\Users\\malte\\Downloads\\maltesh b k resume.pdf");

			FileSystemResource file1 = new FileSystemResource(file);
			System.out.println(file.getName());
			helper.addAttachment(file.getName(), file);

			mailsender.send(memi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	@GetMapping("/pdf")
	public ResponseEntity<byte[]> pdf(HttpServletRequest request , HttpServletResponse response) throws IOException{
		
		
		HttpHeaders header = new HttpHeaders();
//		header.add("Content-Disposition", "inline; filename=citiesreport.pdf");
//		header.setContentDisposition(ContentDisposition.parse("inline; filename=citiesreport.pdf"));
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		FileOutputStream f = new FileOutputStream("2.pdf");
		
		Document doc =  new Document();
		
		PdfWriter.getInstance(doc, out);
		doc.open();
		doc.add(new Paragraph("hi this is malteshdd"));
		doc.close();
		
		f.write(out.toByteArray());
		

		
		return ResponseEntity.ok()
		.contentType(MediaType.APPLICATION_PDF)
		.header(HttpHeaders.CONTENT_DISPOSITION.concat("attachment;filename=12.pdf"))
		.body(out.toByteArray());

	}
}
