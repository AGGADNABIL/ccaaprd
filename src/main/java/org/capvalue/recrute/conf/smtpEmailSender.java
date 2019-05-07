package org.capvalue.recrute.conf;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
@Component
public class smtpEmailSender {
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void send(String to,String subject,String body) throws MessagingException{
		//MimeMessage message=javaMailSender.createMimeMessage();
		//javaMailSender=new JavaMailSenderImpl();
		MimeMessage message=javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper=new MimeMessageHelper(message,true);
		helper.setFrom("recrutement@capvalue.info") ;
		//helper.setU
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body,true);
		javaMailSender.send(message);
	}

	
	/*@Autowired
	public EmailService emailService;

	public void sendEmail(){
	   final Email email = EmailImpl.builder()
	        .from(new InternetAddress("mymail@mail.co.uk"))
	        .replyTo(new InternetAddress("someone@localhost"))
	        .to(Lists.newArrayList(new InternetAddress("someone@localhost")))
	        .subject("Lorem ipsum")
	        .body("Lorem ipsum dolor sit amet [...]");
	        //.encoding(Charset.forName("UTF-8")).build();

	   emailService.send(email);
	}*/
}
