package dev.pitt.loans.loan_project.services.core.loan;

import dev.pitt.loans.loan_project.dto.request.LoanSimulationRequestDTO;
import dev.pitt.loans.loan_project.dto.response.LoanResponseDTO;
import dev.pitt.loans.loan_project.services.base.ServiceException;

//paso 0
@FunctionalInterface
public interface LoanSimulatorService {
    LoanResponseDTO simulateLoan(LoanSimulationRequestDTO loanSimulationRequestDTO) throws ServiceException;
}
