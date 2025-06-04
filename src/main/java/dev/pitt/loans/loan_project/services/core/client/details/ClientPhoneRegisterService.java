package dev.pitt.loans.loan_project.services.core.client.details;

import dev.pitt.loans.loan_project.dto.request.ClientPhoneRequestDTO;
import dev.pitt.loans.loan_project.dto.response.ClientPhoneResponseDTO;
import dev.pitt.loans.loan_project.services.base.crud.GenericSaveService;

@FunctionalInterface
public interface ClientPhoneRegisterService  {

    ClientPhoneResponseDTO phoneRegister(ClientPhoneRequestDTO clientPhoneRequestDTO);
}
