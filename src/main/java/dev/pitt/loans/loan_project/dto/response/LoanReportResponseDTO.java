package dev.pitt.loans.loan_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanReportResponseDTO {
    private int totalLoans;
    private BigDecimal totalAmount;
    private int approvedLoans;
    private int rejectedLoans;
    private int underReviewLoans;
    private LocalDateTime reportDate;
}
