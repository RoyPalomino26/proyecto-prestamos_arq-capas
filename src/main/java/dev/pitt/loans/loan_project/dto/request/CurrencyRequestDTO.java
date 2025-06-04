package dev.pitt.loans.loan_project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRequestDTO {
    private String name;
    private String symbol;
    private String codeIso;
}
