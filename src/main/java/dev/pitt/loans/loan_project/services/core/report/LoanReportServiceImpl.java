package dev.pitt.loans.loan_project.services.core.report;

import dev.pitt.loans.loan_project.dto.response.LoanReportResponseDTO;
import dev.pitt.loans.loan_project.entity.core.oracle.loan.LoanEntity;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.LoanRepository;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LoanReportServiceImpl implements LoanReportService{

    private final LoanRepository loanRepository;

    @Override
    public LoanReportResponseDTO getLoanReport() throws ServiceException {

        List<LoanEntity> loanEntities = loanRepository.findAll();

        if (loanEntities.isEmpty() || loanEntities == null) {
            return new LoanReportResponseDTO();
        }

        int totalLoans = loanEntities.size();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int approvedLoans = 0;
        int rejectedLoans = 0;
        int underReviewLoans = 0;

        for (LoanEntity loan : loanEntities) {
            totalAmount = totalAmount.add(loan.getAmount());
            switch (loan.getStatus()) {
                case APPROVED:
                    approvedLoans++;
                    break;
                case REJECTED:
                    rejectedLoans++;
                    break;
                case UNDER_REVIEW:
                    underReviewLoans++;
                    break;
                default:
                    break;
            }
        }

        LoanReportResponseDTO report = LoanReportResponseDTO.builder()
                .totalLoans(totalLoans)
                .totalAmount(totalAmount)
                .approvedLoans(approvedLoans)
                .rejectedLoans(rejectedLoans)
                .underReviewLoans(underReviewLoans)
                .reportDate(LocalDateTime.now())
                .build();

        return report;
    }

}
