package dev.pitt.loans.loan_project.services.integration.ws.external.reniec.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReniecClientModel {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String tipoDocumento;
    private String numeroDocumento;
    private String digitoVerificador;
}
