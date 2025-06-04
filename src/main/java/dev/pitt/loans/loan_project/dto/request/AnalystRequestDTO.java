package dev.pitt.loans.loan_project.dto.request;

import dev.pitt.loans.loan_project.entity.enums.AnalystRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalystRequestDTO{

        private Long analystId;
        private String fullNameAnalyst;
        private String emailAnalyst;
        private AnalystRole roleAnalyst;

}
