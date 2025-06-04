package dev.pitt.loans.loan_project.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JuridicalClientResponseDTO {
    private Long juridicalClientId;
    private String ruc;
    private String companyName;
    private String legalRepresentative;
    private String address;
    private String email;
    private String district;
    private String province;
    private String department;
    private String typeJuridicalClient;
    private List<ClientPhoneResponseDTO> phones;
    private List<ClientDocumentResponseDTO> documents;
    private List<LoanResponseDTO> loans;
}
