package dev.pitt.loans.loan_project.entity.core.postgres.finance;

import dev.pitt.loans.loan_project.entity.base.GenericEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// POSTGRES

@Table(name = "INTEREST_RATES")
@Entity
@Data
public class InterestRateEntity extends GenericEntity {

    @Id
    @Column(name = "interest_rate_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqInterestRate")
    @SequenceGenerator(sequenceName = "SEQ_INTEREST_RATE", allocationSize = 1, name = "seqInterestRate")
    private Long interestRateId;
    @Column(name = "rate_name", nullable = false, length = 100)
    private String name;
    @Column(name = "rate_value", nullable = false, precision = 5, scale = 2)
    private BigDecimal rateValue;
    @Column(nullable = false)
    private LocalDateTime validFrom;
    @Column(nullable = false)
    private LocalDateTime validTo;

}
