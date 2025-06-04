package dev.pitt.loans.loan_project.entity.base;

import dev.pitt.loans.loan_project.entity.enums.EntityStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class GenericEntity {

    @Enumerated(EnumType.STRING)
    @Column( nullable = false, length = 20)
    protected EntityStatus status;

    @Column( nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = EntityStatus.ACTIVE;
    }

}
