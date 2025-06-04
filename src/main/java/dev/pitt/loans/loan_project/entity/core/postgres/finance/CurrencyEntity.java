package dev.pitt.loans.loan_project.entity.core.postgres.finance;

// POSTGRES

import dev.pitt.loans.loan_project.entity.base.GenericEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CURRENCIES")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyEntity extends GenericEntity {

    @Id
    @Column(name = "currency_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCurrency")
    @SequenceGenerator(sequenceName = "SEQ_CURRENCY", allocationSize = 1, name = "seqCurrency")
    private Long currencyId;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 6)
    private String symbol;
    @Column(nullable = false, length = 3)
    private String codeIso;

}
