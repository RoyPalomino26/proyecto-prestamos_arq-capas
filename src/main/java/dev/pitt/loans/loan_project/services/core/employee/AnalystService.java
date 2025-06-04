package dev.pitt.loans.loan_project.services.core.employee;

import dev.pitt.loans.loan_project.dto.request.AnalystRequestDTO;
import dev.pitt.loans.loan_project.dto.response.AnalystResponseDTO;
import dev.pitt.loans.loan_project.services.base.crud.GenericCrudService;

public interface AnalystService extends
        GenericCrudService<AnalystRequestDTO, AnalystResponseDTO,Long> {
}
