package dev.pitt.loans.loan_project.services.core.report;

import dev.pitt.loans.loan_project.dto.response.LoanReportResponseDTO;
import dev.pitt.loans.loan_project.services.base.ServiceException;

public interface LoanReportService {
    LoanReportResponseDTO getLoanReport() throws ServiceException;
}
