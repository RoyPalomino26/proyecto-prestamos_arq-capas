package dev.pitt.loans.loan_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterestRateResponseDTO {
    private Long interestRateId;
    private String name;
    private BigDecimal rateValue;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
}
