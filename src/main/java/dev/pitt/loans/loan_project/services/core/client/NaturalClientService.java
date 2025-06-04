package dev.pitt.loans.loan_project.services.core.client;

import dev.pitt.loans.loan_project.dto.request.NaturalClientRequestDTO;
import dev.pitt.loans.loan_project.dto.response.NaturalClientResponseDTO;
import dev.pitt.loans.loan_project.services.base.crud.GenericFindByIdService;
import dev.pitt.loans.loan_project.services.base.crud.GenericGetAllService;
import dev.pitt.loans.loan_project.services.base.crud.GenericSaveService;

public interface NaturalClientService extends
        GenericSaveService<NaturalClientRequestDTO, NaturalClientResponseDTO>,
        GenericFindByIdService<NaturalClientResponseDTO, Long>,
        GenericGetAllService<NaturalClientResponseDTO> {
}
