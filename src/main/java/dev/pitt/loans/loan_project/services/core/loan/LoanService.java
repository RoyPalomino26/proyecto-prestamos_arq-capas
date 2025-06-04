package dev.pitt.loans.loan_project.services.core.loan;

import dev.pitt.loans.loan_project.dto.response.LoanReportResponseDTO;
import dev.pitt.loans.loan_project.dto.response.LoanResponseDTO;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.base.crud.GenericFindByIdService;
import dev.pitt.loans.loan_project.services.base.crud.GenericGetAllService;

public interface LoanService extends GenericFindByIdService<LoanResponseDTO, Long>{

}
