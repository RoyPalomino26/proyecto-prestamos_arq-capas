package dev.pitt.loans.loan_project.services.core.client;

import dev.pitt.loans.loan_project.dto.request.JuridicalClientRequestDTO;
import dev.pitt.loans.loan_project.dto.response.JuridicalClientResponseDTO;
import dev.pitt.loans.loan_project.services.base.crud.GenericFindByIdService;
import dev.pitt.loans.loan_project.services.base.crud.GenericGetAllService;
import dev.pitt.loans.loan_project.services.base.crud.GenericSaveService;

public interface JuridicalClientService extends
        GenericSaveService<JuridicalClientRequestDTO, JuridicalClientResponseDTO>,
        GenericFindByIdService<JuridicalClientResponseDTO, Long>,
        GenericGetAllService<JuridicalClientResponseDTO> {
}
