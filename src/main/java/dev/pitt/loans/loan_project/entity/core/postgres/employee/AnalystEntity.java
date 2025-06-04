package dev.pitt.loans.loan_project.entity.core.postgres.employee;

import dev.pitt.loans.loan_project.entity.base.GenericEntity;
import dev.pitt.loans.loan_project.entity.core.oracle.loan.LoanTrackingEntity;
import dev.pitt.loans.loan_project.entity.enums.AnalystRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

// POSTGRES

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ANALYSTS")
@Entity
public class AnalystEntity extends GenericEntity {

    @Id
    @Column(name = "analyst_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAnalyst")
    @SequenceGenerator(sequenceName = "SEQ_ANALYST", allocationSize = 1, name = "seqAnalyst")
    private Long analystId;
    @Column(nullable = false, length = 200)
    private String fullName;
    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private AnalystRole role;

    //@OneToMany(mappedBy = "analyst", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private List<LoanTrackingEntity> loanTrackings;

}
