package dev.pitt.loans.loan_project.dto.request;

import dev.pitt.loans.loan_project.entity.enums.ClientType;
import dev.pitt.loans.loan_project.entity.enums.PhoneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPhoneRequestDTO {

    private Long clientId;
    private ClientType typeClient;// debe ser pasado por pathvariable
    private PhoneType phoneType;
    private String phoneNumber;

}
