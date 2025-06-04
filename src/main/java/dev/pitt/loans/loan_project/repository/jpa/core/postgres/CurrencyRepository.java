package dev.pitt.loans.loan_project.repository.jpa.core.postgres;

import dev.pitt.loans.loan_project.entity.core.postgres.finance.CurrencyEntity;
import dev.pitt.loans.loan_project.repository.jpa.base.GenericRepository;



public interface CurrencyRepository extends GenericRepository<CurrencyEntity, Long> {
}
