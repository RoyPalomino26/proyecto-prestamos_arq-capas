package dev.pitt.loans.loan_project.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.pitt.loans.loan_project.entity.enums.AnalystRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalystResponseDTO {

    private Long analystId;
    private String fullNameAnalyst;
    private String emailAnalyst;
    private AnalystRole role;

    private Object idsLoanTracking;

}
