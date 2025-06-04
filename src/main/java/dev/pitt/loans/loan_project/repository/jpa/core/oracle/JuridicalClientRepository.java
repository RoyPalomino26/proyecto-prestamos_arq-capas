package dev.pitt.loans.loan_project.repository.jpa.core.oracle;

import dev.pitt.loans.loan_project.entity.core.oracle.client.JuridicalClientEntity;
import dev.pitt.loans.loan_project.repository.jpa.base.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuridicalClientRepository extends GenericRepository<JuridicalClientEntity, Long> {
}
