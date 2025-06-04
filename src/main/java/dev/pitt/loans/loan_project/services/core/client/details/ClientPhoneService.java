package dev.pitt.loans.loan_project.services.core.client.details;

import dev.pitt.loans.loan_project.dto.request.ClientPhoneRequestDTO;
import dev.pitt.loans.loan_project.dto.response.ClientPhoneResponseDTO;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.base.crud.GenericDeleteService;
import dev.pitt.loans.loan_project.services.base.crud.GenericFindByIdService;
import dev.pitt.loans.loan_project.services.base.crud.GenericGetAllService;
import dev.pitt.loans.loan_project.services.base.crud.GenericUpdateService;

public interface ClientPhoneService extends
        GenericFindByIdService<ClientPhoneResponseDTO, Long>,
        GenericGetAllService<ClientPhoneResponseDTO>,
        GenericUpdateService<ClientPhoneRequestDTO, ClientPhoneResponseDTO, Long>,
        GenericDeleteService<Long> {

    ClientPhoneResponseDTO phoneRegister(ClientPhoneRequestDTO clientPhoneRequestDTO) throws ServiceException;
}
