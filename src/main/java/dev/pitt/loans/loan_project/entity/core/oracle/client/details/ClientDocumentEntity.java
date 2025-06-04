package dev.pitt.loans.loan_project.entity.core.oracle.client.details;

import dev.pitt.loans.loan_project.entity.base.ClientEntity;
import dev.pitt.loans.loan_project.entity.base.GenericEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table(name = "CLIENT_DOCUMENTS")
@Entity
public class ClientDocumentEntity extends GenericEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqDocument")
    @SequenceGenerator(sequenceName = "SEQ_DOCUMENT", allocationSize = 1, name = "seqDocument")
    private Long documentId;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false)
    private ClientEntity client;

    @Column(nullable = false, length = 20)
    private String document_type;

    private String file_path;
    private LocalDateTime uploaded_at;
}
