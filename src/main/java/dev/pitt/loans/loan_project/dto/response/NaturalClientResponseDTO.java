package dev.pitt.loans.loan_project.dto.response;

import dev.pitt.loans.loan_project.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaturalClientResponseDTO {
    private Long naturalClientId;
    private String dni;
    private String names;
    private String lastName;
    private String ocupation;
    private Gender gender;
    private LocalDateTime birthDate;
    private String address;
    private String email;
    private List<ClientPhoneResponseDTO> phones;
    private List<ClientDocumentResponseDTO> documents;
    private List<LoanResponseDTO> loans;
}
