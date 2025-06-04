package dev.pitt.loans.loan_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponseDTO {
    private Long currencyId;
    private String name;
    private String symbol;
    private String codeIso;
}
