package dev.pitt.loans.loan_project.repository.jpa.core.postgres;

import dev.pitt.loans.loan_project.entity.core.postgres.finance.InterestRateEntity;
import dev.pitt.loans.loan_project.repository.jpa.base.GenericRepository;

public interface InterestRateRepository extends GenericRepository<InterestRateEntity, Long> {
}
