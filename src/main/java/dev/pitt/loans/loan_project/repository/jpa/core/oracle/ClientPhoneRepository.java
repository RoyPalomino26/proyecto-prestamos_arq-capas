package dev.pitt.loans.loan_project.repository.jpa.core.oracle;


import dev.pitt.loans.loan_project.entity.core.oracle.client.details.ClientPhoneEntity;
import dev.pitt.loans.loan_project.repository.jpa.base.GenericRepository;

import java.util.List;

public interface ClientPhoneRepository extends GenericRepository<ClientPhoneEntity, Long> {

    List<ClientPhoneEntity> findClientPhoneEntitiesByClient_ClientId(Long clientClientId);

}
