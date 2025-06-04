package dev.pitt.loans.loan_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaturalClientReniecResponseDTO {
    private String names;
    private String lastName;
    private String dni;
}
