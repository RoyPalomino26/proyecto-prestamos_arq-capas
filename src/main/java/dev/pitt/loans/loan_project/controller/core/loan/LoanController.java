package dev.pitt.loans.loan_project.controller.core.loan;

import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.LoanApplicationRequestDTO;
import dev.pitt.loans.loan_project.dto.request.LoanProcessRequestDTO;
import dev.pitt.loans.loan_project.dto.request.LoanSimulationRequestDTO;
import dev.pitt.loans.loan_project.dto.response.LoanResponseDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static dev.pitt.loans.loan_project.controller.constants.ApiEndPointsConstant.API_LOAN;

@RequestMapping(API_LOAN)
public interface LoanController {

    @Operation(summary = "Simulate a loan",
            description = "Simulates a loan based on the provided loan simulation request data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan simulated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class),
                            examples = @ExampleObject(value = "{ " +
                                    "\"loanId\": 1, " +
                                    "\"amount\": 10000, " +
                                    "\"interestRate\": 5.5, " +
                                    "\"termMonths\": 12, " +
                                    "\"monthlyPayment\": 860.66, " +
                                    "\"status\": \"SIMULATION\" " +
                                    "}"))
            }),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping("/simulate")
    ResponseEntity<CustomResponse> simulateLoan(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Loan simulation request data"
            )
            @RequestBody LoanSimulationRequestDTO loanSimulationRequestDTO,
            BindingResult result)
            throws ControllerException, NotContentException;

    //------------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Request a loan", description = "Requests a new loan based on the provided loan application request data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan request successfully ", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class),
                            examples = @ExampleObject(value = "{ " +
                                    "\"loanId\": 1, " +
                                    "\"amount\": 10000, " +
                                    "\"interestRate\": 5.5, " +
                                    "\"termMonths\": 12, " +
                                    "\"monthlyPayment\": 860.66, " +
                                    "\"status\": \"LOAN_REQUESTED\" " +
                                    "}"))
            }),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping("/request")
    ResponseEntity<CustomResponse> requestLoan(
            @Valid  @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO,
            BindingResult result)
            throws ControllerException, NotContentException;

    //------------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Review a loan",
            description = "Reviews a loan application based on the provided loan process request data and loan ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan review successfully "),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{loanId}/review")
    ResponseEntity<CustomResponse> reviewLoan(
            @RequestBody LoanProcessRequestDTO loanProcessRequestDTO,
            @Parameter(description = "id del Loan") @PathVariable(value = "loanId") Long loanId)
            throws ControllerException, NotContentException;

    //------------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Approve a loan",
            description = "Approves a loan application based on the provided loan process request data and loan ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan approve successfully "),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{loanId}/approve")
    ResponseEntity<CustomResponse> approveLoan(
            @RequestBody LoanProcessRequestDTO loanProcessRequestDTO,
            @Parameter(description = "id del Loan") @PathVariable(value = "loanId") Long loanId)
            throws ControllerException, NotContentException;

    //------------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Reject a loan",
            description = "Rejects a loan application based on the provided loan process request data and loan ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan reject successfully "),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{loanId}/reject")
    ResponseEntity<CustomResponse> rejectLoan(
            @RequestBody LoanProcessRequestDTO loanProcessRequestDTO,
            @Parameter(description = "id del Loan") @PathVariable(value = "loanId") Long loanId)
            throws ControllerException, NotContentException;

    //------------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Get loan by ID",
            description = "Retrieves loan details based on the provided loan ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan find ", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class),
                            examples = @ExampleObject(value = "{ " +
                                    "\"loanId\": 1, " +
                                    "\"amount\": 10000, " +
                                    "\"interestRate\": 5.5, " +
                                    "\"termMonths\": 12, " +
                                    "\"monthlyPayment\": 860.66, " +
                                    "\"status\": \"LOAN_REQUESTED\" " +
                                    "}"))
            }),
            @ApiResponse(responseCode = "204", description = "Loan not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<CustomResponse> getById(@Parameter(description = "id del Loan") @PathVariable(value = "id") Long id)
            throws ControllerException, NotContentException;
}
