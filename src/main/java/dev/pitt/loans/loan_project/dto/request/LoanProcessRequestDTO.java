package dev.pitt.loans.loan_project.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.pitt.loans.loan_project.entity.enums.LoanProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanProcessRequestDTO {
    private Long analystId;
    private Long clientId;
    private LoanProcessStatus status;
    private String observations;
}
