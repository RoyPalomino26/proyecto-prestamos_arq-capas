package dev.pitt.loans.loan_project.entity.core.oracle.loan;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.pitt.loans.loan_project.entity.core.postgres.employee.AnalystEntity;
import dev.pitt.loans.loan_project.entity.enums.LoanProcessStatus;
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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Table(name = "LOAN_TRACKING")
@Entity
public class LoanTrackingEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTracking")
    @SequenceGenerator(sequenceName = "SEQ_TRACKING", allocationSize = 1, name = "seqTracking")
    private Long loanTrackingId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", referencedColumnName = "loan_id", nullable = false)
    private LoanEntity loan;

    @Column(nullable = false, name = "analyst_id")
    private Long analystId;
    //@JsonBackReference
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "analyst_id",referencedColumnName = "analyst_id", nullable = false)
    //private AnalystEntity analyst; //todo: Long analystId;

    private String observations;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private LoanProcessStatus status;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
