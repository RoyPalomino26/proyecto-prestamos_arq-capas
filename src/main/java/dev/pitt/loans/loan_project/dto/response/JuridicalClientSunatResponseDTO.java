package dev.pitt.loans.loan_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JuridicalClientSunatResponseDTO {
    private String ruc;
    private String companyName;
    private String address;
    private String district;
    private String province;
    private String department;
    private String typeJuridicalClient;
    private String economicActivity;
}
