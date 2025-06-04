package dev.pitt.loans.loan_project.controller.core.loan;

import dev.pitt.loans.loan_project.controller.base.GenericController;
import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.LoanApplicationRequestDTO;
import dev.pitt.loans.loan_project.dto.request.LoanProcessRequestDTO;
import dev.pitt.loans.loan_project.dto.request.LoanSimulationRequestDTO;
import dev.pitt.loans.loan_project.dto.response.LoanResponseDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.loan.LoanProcessingService;
import dev.pitt.loans.loan_project.services.core.loan.LoanRequestedService;
import dev.pitt.loans.loan_project.services.core.loan.LoanService;
import dev.pitt.loans.loan_project.services.core.loan.LoanSimulatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static dev.pitt.loans.loan_project.controller.constants.ApiMessageConstant.API_MSG_RESPONSE_INVALID_ID;

@RequiredArgsConstructor
@RestController
public class LoanControllerImpl extends GenericController implements LoanController {

    private final LoanService loanService;
    private final LoanSimulatorService loanSimulatorService;
    private final LoanRequestedService loanRequestedService;
    private final LoanProcessingService loanProcessService;


    @Override
    public ResponseEntity<CustomResponse> simulateLoan(
            LoanSimulationRequestDTO loanSimulationRequestDTO,
            BindingResult result) throws ControllerException, NotContentException {
        if (result.hasErrors()) throw new ControllerException(result);

        try{
            LoanResponseDTO loanResponseDTO = loanSimulatorService.simulateLoan(loanSimulationRequestDTO);

            EntityModel<LoanResponseDTO> resource = EntityModel.of(loanResponseDTO,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LoanController.class)
                            .getById(loanResponseDTO.getLoanId()))
                            .withSelfRel());

            Map<String, Object> response = new HashMap<>();
            response.put("LoanSimulation", loanResponseDTO);
            response.put("links", resource.getLinks());

            return super.response(response);

        } catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> requestLoan(
            LoanApplicationRequestDTO loanApplicationRequestDTO,
            BindingResult result) throws ControllerException, NotContentException {
        if (result.hasErrors()) throw new ControllerException(result);

        try{
            LoanResponseDTO loanResponseDTO = loanRequestedService.requestLoan(loanApplicationRequestDTO);

            EntityModel<LoanResponseDTO> resource = EntityModel.of(loanResponseDTO,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LoanController.class)
                                    .getById(loanResponseDTO.getLoanId()))
                            .withSelfRel());

            Map<String, Object> response = new HashMap<>();
            response.put("LoanRequest", loanResponseDTO);
            response.put("links", resource.getLinks());

            return super.response(response);
        } catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> reviewLoan(
            LoanProcessRequestDTO loanProcessRequestDTO, Long loanId) throws ControllerException, NotContentException {
        try{
            loanProcessService.reviewLoan(loanProcessRequestDTO, loanId);

            return super.response("Loan reviewed successfully");
        } catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> approveLoan(
            LoanProcessRequestDTO loanProcessRequestDTO, Long loanId) throws ControllerException, NotContentException {
        try{
            loanProcessService.approveLoan(loanProcessRequestDTO, loanId);

            return super.response("Loan approved successfully");
        } catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> rejectLoan(
            LoanProcessRequestDTO loanProcessRequestDTO, Long loanId) throws ControllerException, NotContentException {
        try{
            loanProcessService.rejectLoan(loanProcessRequestDTO, loanId);

            return super.response("Loan rejected successfully");
        } catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> getById(Long id)
            throws ControllerException, NotContentException {
        if (!ValidIdGen(id)) return super.badRequest(String.format(API_MSG_RESPONSE_INVALID_ID, id));

        try{
            Map<String, Object> response = new HashMap<>();
            response.put("Loan", loanService.findById(id));

            return super.response(response);
        } catch (ServiceException e) {
            return super.internalError();
        }

    }
}
