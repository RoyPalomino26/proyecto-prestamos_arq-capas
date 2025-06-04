package dev.pitt.loans.loan_project.entity.core.oracle.client.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.pitt.loans.loan_project.entity.base.ClientEntity;
import dev.pitt.loans.loan_project.entity.base.GenericEntity;
import dev.pitt.loans.loan_project.entity.enums.PhoneType;
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

@Data
@Table(name = "CLIENT_PHONE_NUMBERS")
@Entity
public class ClientPhoneEntity extends GenericEntity {
    @Id // PRIMARY KEY
    @Column(nullable = false/*NOT NULL*/)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqPhoneNumber")
    @SequenceGenerator(sequenceName = "SEQ_PHONE_NUMBER", allocationSize = 1, name = "seqPhoneNumber")
    private Long phoneId;

    //name client_id -> nombre columna FK en la tabla de phone
    //referencedColumnName client_id -> nombre columna PK en la tabla de client
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false)
    private ClientEntity client;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;//MOBILE - LANDLINE

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(length = 15)
    private String provider;

}
