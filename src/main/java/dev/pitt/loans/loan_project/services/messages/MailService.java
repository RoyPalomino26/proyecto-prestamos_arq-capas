package dev.pitt.loans.loan_project.services.messages;

@FunctionalInterface
public interface MailService {

	void sendMail(String toEmail, String subject, String body);

}