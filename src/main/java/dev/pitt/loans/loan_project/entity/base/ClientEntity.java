package dev.pitt.loans.loan_project.entity.base;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.pitt.loans.loan_project.entity.core.oracle.client.details.ClientDocumentEntity;
import dev.pitt.loans.loan_project.entity.core.oracle.client.details.ClientPhoneEntity;
import dev.pitt.loans.loan_project.entity.core.oracle.loan.LoanEntity;
import dev.pitt.loans.loan_project.entity.enums.ClientType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CLIENTS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ClientEntity extends GenericEntity{
    @Id
    @Column(name = "client_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqClient")
    @SequenceGenerator(sequenceName = "SEQ_CLIENT", allocationSize = 1, name = "seqClient")
    protected Long clientId;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false, length = 10)
    protected ClientType clientType;

    @Column(nullable = false, length = 100)
    protected String address;

    @Column(unique = true, nullable = false, length = 60)
    protected String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<ClientPhoneEntity> phones;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<ClientDocumentEntity> documents;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<LoanEntity> loans;
}
