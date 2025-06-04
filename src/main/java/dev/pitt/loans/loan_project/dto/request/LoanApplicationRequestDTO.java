package dev.pitt.loans.loan_project.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.pitt.loans.loan_project.entity.enums.ClientType;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanApplicationRequestDTO {
    private Long clientId;
    private ClientType clientType;
    private Double amount;
    private Long interestRateId;
    private Long currencyId;
    private Integer termMonths;
    @Nullable
    private BigDecimal monthlyPayment;
    private List<ClientDocumentRequestDTO> clientDocuments;
}
