package dev.pitt.loans.loan_project.services.core.loan;

import dev.pitt.loans.loan_project.dto.request.LoanProcessRequestDTO;
import dev.pitt.loans.loan_project.services.base.ServiceException;

public interface LoanProcessingService {

    void reviewLoan(LoanProcessRequestDTO loanProcessRequestDTO, Long loanId) throws ServiceException;

    void approveLoan(LoanProcessRequestDTO loanProcessRequestDTO, Long loanId) throws ServiceException;

    void rejectLoan(LoanProcessRequestDTO loanProcessRequestDTO, Long loanId) throws ServiceException;

}
