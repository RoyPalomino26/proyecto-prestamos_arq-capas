package dev.pitt.loans.loan_project.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.pitt.loans.loan_project.entity.enums.Gender;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaturalClientRequestDTO {
    @NotBlank(message = "DNI is mandatory")
    @Size(min = 8, max = 8, message = "DNI must be 8 characters long")
    String dni;

    @NotBlank(message = "Names are mandatory")
    @Size(max = 45, message = "Names must be less than 45 characters")
    String names;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 45, message = "Last name must be less than 45 characters")
    String lastName;

    @NotBlank(message = "Occupation is mandatory")
    @Size(max = 30, message = "Occupation must be less than 30 characters")
    String ocupation;

    @NotNull(message = "Birth date is mandatory")
    @Past(message = "Birth date must be in the past")
    LocalDateTime birth;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 100, message = "Address must be less than 25 characters")
    String address;

    Gender gender;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 60, message = "Email must be less than 35 characters")
    String email;

    @Nullable
    List<ClientPhoneRequestDTO> phones;

    @Nullable
    List<ClientDocumentRequestDTO> documents;
}
