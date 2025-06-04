package dev.pitt.loans.loan_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LoanProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanProjectApplication.class, args);
	}

}
