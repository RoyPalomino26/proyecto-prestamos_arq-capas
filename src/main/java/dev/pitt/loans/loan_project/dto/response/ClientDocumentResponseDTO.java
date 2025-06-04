package dev.pitt.loans.loan_project.dto.response;

import dev.pitt.loans.loan_project.entity.base.ClientEntity;
import dev.pitt.loans.loan_project.entity.enums.EntityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDocumentResponseDTO {
    private Long documentId;
    private Long clientId;
    private String document_type;
    private String file_path;
    private LocalDateTime uploaded_at;
    protected EntityStatus status;
}
