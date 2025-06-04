package dev.pitt.loans.loan_project.services.core.client.details;

import dev.pitt.loans.loan_project.dto.request.ClientDocumentRequestDTO;
import dev.pitt.loans.loan_project.dto.response.ClientDocumentResponseDTO;
import dev.pitt.loans.loan_project.services.base.crud.GenericCrudService;

public interface ClientDocumentService
        extends GenericCrudService<ClientDocumentRequestDTO, ClientDocumentResponseDTO,Long> {
}
