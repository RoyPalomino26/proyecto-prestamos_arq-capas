package dev.pitt.loans.loan_project.repository.jpa.core.postgres;


import dev.pitt.loans.loan_project.entity.core.postgres.employee.AnalystEntity;
import dev.pitt.loans.loan_project.repository.jpa.base.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalystRepository extends GenericRepository<AnalystEntity, Long> {
}
