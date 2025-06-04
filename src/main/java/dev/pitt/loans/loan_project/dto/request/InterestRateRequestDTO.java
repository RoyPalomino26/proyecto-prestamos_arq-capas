package dev.pitt.loans.loan_project.dto.request;

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
public class InterestRateRequestDTO {
    private String name;
    private BigDecimal rateValue;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
}
