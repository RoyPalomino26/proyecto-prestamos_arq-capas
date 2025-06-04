package dev.pitt.loans.loan_project.controller.core.clients;

import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.JuridicalClientRequestDTO;
import dev.pitt.loans.loan_project.dto.response.JuridicalClientResponseDTO;
import dev.pitt.loans.loan_project.dto.response.JuridicalClientSunatResponseDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static dev.pitt.loans.loan_project.controller.constants.ApiEndPointsConstant.API_JURIDICAL_CLIENT;

@RequestMapping(API_JURIDICAL_CLIENT)
public interface JuridicalClientController {

    @Operation(summary = "Get company information from SUNAT",
            description = "Retrieves company information from SUNAT based on the provided RUC.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company information retrieved successfully",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = JuridicalClientSunatResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Company information not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/sunat/{ruc}")
    ResponseEntity<CustomResponse> getCompanySunat(
            @Parameter(description = "ruc de JuridicalClient") @PathVariable(value = "ruc") String ruc)
            throws ControllerException, NotContentException;

    //------------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Save juridical client",
            description = "Saves a new juridical client based on the provided juridical client request data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juridical client saved successfully",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = JuridicalClientResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    ResponseEntity<CustomResponse> save(
            @Valid @RequestBody JuridicalClientRequestDTO juridicalClientRequestDTO, BindingResult result)
            throws ControllerException, NotContentException;

    //------------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Get juridical client by ID",
            description = "Retrieves juridical client details based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juridical client retrieved successfully",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = JuridicalClientSunatResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Company information not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<CustomResponse> getById(
            @Parameter(description = "id de JuridicalClient") @PathVariable(value = "id") Long id)
            throws ControllerException, NotContentException;

    @GetMapping
    ResponseEntity<CustomResponse> getAll() throws ControllerException, NotContentException;
}
