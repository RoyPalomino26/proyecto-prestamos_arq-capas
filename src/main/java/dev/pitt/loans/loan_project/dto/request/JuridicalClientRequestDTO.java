package dev.pitt.loans.loan_project.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JuridicalClientRequestDTO {
    @NotBlank(message = "RUC is mandatory")
    @Size(min = 11, max = 12, message = "RUC must be 11 or 12 characters long")
    private String ruc;

    @NotBlank(message = "Company name is mandatory")
    @Size(max = 200, message = "Company name must be less than 200 characters")
    private String companyName;

    @NotBlank(message = "Legal representative is mandatory")
    @Size(max = 100, message = "Legal representative must be less than 100 characters")
    private String legalRepresentative;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 60, message = "Email must be less than 35 characters")
    private String email;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 100, message = "Address must be less than 100 characters")
    private String address;

    @NotBlank(message = "District is mandatory")
    private String district;

    @NotBlank(message = "Province is mandatory")
    private String province;

    @NotBlank(message = "Department is mandatory")
    private String department;

    @NotBlank(message = "Type juridical client is mandatory")
    private String typeJuridicalClient;

    @NotBlank(message = "Activity economic is mandatory")
    private String activityEconomic;

    @Nullable
    List<ClientPhoneRequestDTO> phones;

    @Nullable
    List<ClientDocumentRequestDTO> documents;
}
