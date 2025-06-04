package dev.pitt.loans.loan_project.services.core.finance;

import dev.pitt.loans.loan_project.dto.request.CurrencyRequestDTO;
import dev.pitt.loans.loan_project.dto.response.CurrencyResponseDTO;
import dev.pitt.loans.loan_project.services.base.crud.GenericCrudService;

public interface CurrencyService extends
        GenericCrudService<CurrencyRequestDTO, CurrencyResponseDTO,Long> {
}
