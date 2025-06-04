package dev.pitt.loans.loan_project.entity.core.oracle.client;

import dev.pitt.loans.loan_project.entity.base.ClientEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "JURIDICAL_CLIENTS")
@Entity
public class JuridicalClientEntity extends ClientEntity {

    @Column(nullable = false, length = 12, unique = true)
    private String ruc;

    @Column(nullable = false, length = 200)
    private String companyName;

    @Column(nullable = false)
    private String legalRepresentative;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false, length = 100)
    private String province;

    @Column(nullable = false, length = 100)
    private String department;

    @Column(nullable = false, length = 100)
    private String typeJuridicalClient;

    @Column(nullable = false, length = 100)
    private String activityEconomic;

}
