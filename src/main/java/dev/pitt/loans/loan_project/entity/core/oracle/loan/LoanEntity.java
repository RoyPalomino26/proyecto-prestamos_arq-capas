package dev.pitt.loans.loan_project.entity.core.oracle.loan;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.pitt.loans.loan_project.entity.base.ClientEntity;
import dev.pitt.loans.loan_project.entity.core.postgres.finance.CurrencyEntity;
import dev.pitt.loans.loan_project.entity.core.postgres.finance.InterestRateEntity;
import dev.pitt.loans.loan_project.entity.enums.LoanProcessStatus;
import dev.pitt.loans.loan_project.entity.enums.LoanStage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "LOANS")
@Entity
public class LoanEntity {

    @Id
    @Column(name = "loan_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLoan")
    @SequenceGenerator(sequenceName = "SEQ_LOAN", allocationSize = 1, name = "seqLoan")
    private Long loanId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false)
    private ClientEntity client;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "interest_rate_id",nullable = false)
    private Long interestRateId;
    //@ManyToOne
    //@JoinColumn(name = "interest_rate_id", referencedColumnName = "interest_rate_id", nullable = false)
    //private InterestRateEntity interestRate;// todo: Long interestRateId;

    @Column(nullable = false,name = "currency_id")
    private Long currencyId;
    //@ManyToOne
    //@JoinColumn(name = "currency_id", referencedColumnName = "currency_id", nullable = false)
    //private CurrencyEntity currency;// todo: Long currencyId;

    @Column(nullable = false)
    private Integer termMonths;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal monthlyPayment;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private LoanStage stage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanProcessStatus status;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanTrackingEntity> loanTrackings;

}
