package dev.pitt.loans.loan_project.services.messages;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j // Log de Lombok
@Component
public class MailServiceImpl implements MailService {

	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String  mailFromUserName;

	@Override
	public void sendMail(String toEmail, String subject, String body) {
		try {
			
			log.info("Enviando mail a.. {}", toEmail);
			
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
			messageHelper.setSubject(subject);
			messageHelper.setText(body, true);
			messageHelper.setFrom(mailFromUserName);
			messageHelper.setTo(toEmail);
			
			mailSender.send(message);
			
			log.info("Mail enviado exitosamente");
		} catch (MessagingException ex) {
			log.error("Error al enviar mail a {}", toEmail);
		}
	}
}
