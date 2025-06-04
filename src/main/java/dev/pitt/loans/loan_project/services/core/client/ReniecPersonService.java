package dev.pitt.loans.loan_project.services.core.client;

import dev.pitt.loans.loan_project.dto.response.NaturalClientReniecResponseDTO;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.integration.ws.external.reniec.model.ReniecClientModel;

import java.util.Optional;

@FunctionalInterface
public interface ReniecPersonService {

    Optional<NaturalClientReniecResponseDTO> getPersonReniec(String dni) throws ServiceException;

}
