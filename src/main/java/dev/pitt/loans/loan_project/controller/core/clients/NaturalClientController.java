package dev.pitt.loans.loan_project.controller.core.clients;

import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.NaturalClientRequestDTO;
import dev.pitt.loans.loan_project.dto.response.NaturalClientReniecResponseDTO;
import dev.pitt.loans.loan_project.dto.response.NaturalClientResponseDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import io.swagger.v3.oas.annotations.Operation;
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

import static dev.pitt.loans.loan_project.controller.constants.ApiEndPointsConstant.API_NATURAL_CLIENT;

@RequestMapping(API_NATURAL_CLIENT)
public interface NaturalClientController {

    @Operation(summary = "Get person information from RENIEC",
            description = "Retrieves person information from RENIEC based on the provided DNI.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person information retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NaturalClientReniecResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Person information not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/reniec/{dni}")
    ResponseEntity<CustomResponse> getPersonReniec(@PathVariable(value = "dni") String dni)
            throws ControllerException, NotContentException;

    //------------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Save natural client",
            description = "Saves a new natural client based on the provided natural client request data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Natural client saved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NaturalClientResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    ResponseEntity<CustomResponse>save(
            @Valid @RequestBody NaturalClientRequestDTO naturalClientRequestDTO, BindingResult result)
            throws ControllerException, NotContentException;

    //------------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Get natural client by ID",
            description = "Retrieves natural client details based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Natural client retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NaturalClientResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Natural client not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<CustomResponse> getById(@PathVariable(value = "id") Long id)
            throws ControllerException, NotContentException;

    @GetMapping
    ResponseEntity<CustomResponse> getAll() throws ControllerException, NotContentException;
}
