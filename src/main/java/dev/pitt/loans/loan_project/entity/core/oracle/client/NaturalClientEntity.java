package dev.pitt.loans.loan_project.entity.core.oracle.client;

import dev.pitt.loans.loan_project.entity.base.ClientEntity;
import dev.pitt.loans.loan_project.entity.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "NATURAL_CLIENTS")
@Entity
public class NaturalClientEntity extends ClientEntity {

    @Column(nullable = false, length = 8, unique = true)
    private String dni;

    @Column(nullable = false, length = 45)
    private String names;

    @Column(nullable = false, length = 45)
    private String lastName;

    @Column(nullable = false, length = 30)
    private String ocupation;

    @Column(nullable = false)
    private LocalDateTime birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
