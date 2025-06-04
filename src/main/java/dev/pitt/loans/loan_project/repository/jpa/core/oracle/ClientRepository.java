package dev.pitt.loans.loan_project.repository.jpa.core.oracle;

import dev.pitt.loans.loan_project.entity.base.ClientEntity;
import dev.pitt.loans.loan_project.repository.jpa.base.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends GenericRepository<ClientEntity, Long> {

    //TODO: Add custom queries here
}
