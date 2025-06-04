package dev.pitt.loans.loan_project.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.pitt.loans.loan_project.entity.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDocumentRequestDTO {

    private Long clientId;
    private ClientType clientType;
    private String document_type;
    private String file_path;

}
