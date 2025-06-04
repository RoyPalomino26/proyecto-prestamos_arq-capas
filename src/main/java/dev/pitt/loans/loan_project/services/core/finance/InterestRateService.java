package dev.pitt.loans.loan_project.services.core.finance;

import dev.pitt.loans.loan_project.dto.request.InterestRateRequestDTO;
import dev.pitt.loans.loan_project.dto.response.InterestRateResponseDTO;
import dev.pitt.loans.loan_project.services.base.crud.GenericCrudService;

public interface InterestRateService extends
        GenericCrudService<InterestRateRequestDTO, InterestRateResponseDTO,Long> {
}
