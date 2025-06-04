package dev.pitt.loans.loan_project.dto.response;

import dev.pitt.loans.loan_project.entity.enums.PhoneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPhoneResponseDTO {

    private Long phoneId;
    private Long clientId;
    private PhoneType phoneType;
    private String phoneNumber;

}
