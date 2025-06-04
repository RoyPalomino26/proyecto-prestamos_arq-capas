package dev.pitt.loans.loan_project.services.jobs;

import dev.pitt.loans.loan_project.dto.response.LoanReportResponseDTO;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.report.LoanReportService;
import dev.pitt.loans.loan_project.services.messages.MailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobMailService implements Job {
			
	private final MailService mailService;
	private final LoanReportService loanReportService;
	
	public JobMailService(MailService mailService, LoanReportService loanReportService) {
		this.mailService = mailService;
		this.loanReportService = loanReportService;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			LoanReportResponseDTO report = loanReportService.getLoanReport();
			log.info("Mail Job !,{}", context.getRefireCount());

			// Construir el cuerpo del correo
			StringBuilder body = new StringBuilder();
			if(!(report.getReportDate() == null)) {
				body.append("<h1>Reporte de Préstamos</h1>")
						.append("<p>Total de Préstamos: ").append(report.getTotalLoans()).append("</p>")
						.append("<p>Cantidad Total: ").append(report.getTotalAmount()).append("</p>")
						.append("<p>Préstamos Aprobados: ").append(report.getApprovedLoans()).append("</p>")
						.append("<p>Préstamos Rechazados: ").append(report.getRejectedLoans()).append("</p>")
						.append("<p>Préstamos en Revisión: ").append(report.getUnderReviewLoans()).append("</p>")
						.append("<p>Fecha del Reporte: ").append(report.getReportDate()).append("</p>");
			}else {
				body.append("<h1>Reporte de Préstamos</h1>")
						.append("<p>No hay préstamos registrados</p>");
			}

			body.append("<br/><br/>")
					.append("Visítenos en: <a href='#'>Bank YARA</a>")
					.append("<br/><br/>")
					.append("Estemos en contacto por este medio");

			mailService.sendMail("palominoriosroy@gmail.com", "Reporte de Préstamos", body.toString());
		} catch (ServiceException e) {
			log.error("Error al generar el reporte de préstamos", e);
			throw new JobExecutionException(e);
		}
	}
}
