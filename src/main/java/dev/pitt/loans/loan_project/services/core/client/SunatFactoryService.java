package dev.pitt.loans.loan_project.services.core.client;

import dev.pitt.loans.loan_project.dto.response.JuridicalClientSunatResponseDTO;
import dev.pitt.loans.loan_project.services.base.ServiceException;

import java.util.Optional;

@FunctionalInterface
public interface SunatFactoryService {

    Optional<JuridicalClientSunatResponseDTO> getFactorySunat(String ruc) throws ServiceException;

}
