package dev.pitt.loans.loan_project.controller.core.finance;

import dev.pitt.loans.loan_project.controller.base.GenericController;
import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.InterestRateRequestDTO;
import dev.pitt.loans.loan_project.dto.response.InterestRateResponseDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.finance.InterestRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.pitt.loans.loan_project.controller.constants.ApiMessageConstant.API_MSG_RESPONSE_INVALID_ID;

@RestController
@RequiredArgsConstructor
public class InterestRateControllerImpl extends GenericController implements InterestRateController {

    private final InterestRateService interestRateService;

    @Override
    public ResponseEntity<CustomResponse> save(InterestRateRequestDTO interestRateRequestDTO,
                                               BindingResult result) throws ControllerException, NotContentException {
        if (result.hasErrors()) throw new ControllerException(result);

        try{
            InterestRateResponseDTO savedInterestRate = interestRateService.save(interestRateRequestDTO);

            EntityModel<InterestRateResponseDTO> resource = EntityModel.of(savedInterestRate,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InterestRateController.class)
                            .getById(savedInterestRate.getInterestRateId()))
                            .withSelfRel());

            Map<String, Object> response = new HashMap<>();
            response.put("InterestRate", savedInterestRate);
            response.put("links", resource.getLinks());

            return super.created(response);

        }catch (ServiceException e) {
            return super.internalError();
        }

    }

    @Override
    public ResponseEntity<CustomResponse> getAll()
            throws ControllerException, NotContentException {

        try{

            List<InterestRateResponseDTO> interestRates = interestRateService.getAll();

            Map<String, Object> response = new HashMap<>();
            response.put("InterestRates", interestRates);
            response.put("cantResults", interestRates.size());

            return super.getResponse(response);

        }catch (ServiceException e) {
            return super.internalError();
        }

    }

    @Override
    public ResponseEntity<CustomResponse> getById(Long id)
            throws ControllerException, NotContentException {
        if (!ValidIdGen(id)) return super.badRequest(String.format(API_MSG_RESPONSE_INVALID_ID, id));

        try{

            InterestRateResponseDTO interestRate = interestRateService.findById(id);
            Map<String, Object> response = new HashMap<>();

            response.put("InterestRate", interestRate);

            return super.response(response);

        }catch (ServiceException e) {
            return super.internalError();
        }
    }
}
