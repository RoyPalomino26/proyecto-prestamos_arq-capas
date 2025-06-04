package dev.pitt.loans.loan_project.dto.response;

import dev.pitt.loans.loan_project.entity.enums.LoanProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDTO {
    private Long loanId;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private Integer termMonths;
    private BigDecimal monthlyPayment;
    private LoanProcessStatus status;
}
