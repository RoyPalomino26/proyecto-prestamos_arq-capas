package dev.pitt.loans.loan_project.services.integration.ws.external.sunat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SunatClientModel {
    private String razonSocial;
    private String numeroDocumento;
    private String estado;
    private String condicion;
    private String direccion;
    private String distrito;
    private String provincia;
    private String departamento;
    private String tipo;
    private String actividadEconomica;
}