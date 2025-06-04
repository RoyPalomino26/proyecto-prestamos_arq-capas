package dev.pitt.loans.loan_project.services.core.loan;


import dev.pitt.loans.loan_project.dto.request.LoanApplicationRequestDTO;
import dev.pitt.loans.loan_project.dto.request.LoanSimulationRequestDTO;
import dev.pitt.loans.loan_project.dto.response.LoanResponseDTO;
import dev.pitt.loans.loan_project.services.base.ServiceException;

@FunctionalInterface
public interface LoanRequestedService {

    LoanResponseDTO requestLoan(LoanApplicationRequestDTO loanApplicationRequestDTO)throws ServiceException;

}
