package dev.pitt.loans.loan_project.controller.core.finance;

import dev.pitt.loans.loan_project.controller.base.GenericController;
import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.CurrencyRequestDTO;
import dev.pitt.loans.loan_project.dto.response.CurrencyResponseDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.finance.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.pitt.loans.loan_project.controller.constants.ApiMessageConstant.API_MSG_RESPONSE_INVALID_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyControllerImpl extends GenericController implements CurrencyController {

    private final CurrencyService currencyService;

    @Override
    public ResponseEntity<CustomResponse> save(CurrencyRequestDTO currencyRequestDTO,
                                               BindingResult result) throws ControllerException, NotContentException {
        if (result.hasErrors()) throw new ControllerException(result);

        try {
            CurrencyResponseDTO savedCurrency = currencyService.save(currencyRequestDTO);

            EntityModel<CurrencyResponseDTO> resource = EntityModel.of(savedCurrency,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurrencyController.class)
                                    .getById(savedCurrency.getCurrencyId()))
                            .withSelfRel());

            Map<String, Object> response = new HashMap<>();
            response.put("Currency", savedCurrency);
            response.put("links", resource.getLinks());

            return super.created(response);
        } catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> getAll()
            throws ControllerException, NotContentException {

        try {
            List<CurrencyResponseDTO> currencies = currencyService.getAll();

            Map<String, Object> response = new HashMap<>();
            response.put("Currencies", currencies);
            response.put("cantResult", currencies.size());

            return super.getResponse(response);

        } catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> getById(Long id)
            throws ControllerException, NotContentException {
        if (!ValidIdGen(id)) return super.badRequest(String.format(API_MSG_RESPONSE_INVALID_ID, id));

        try {
            CurrencyResponseDTO currency = currencyService.findById(id);
            Map<String, Object> response = new HashMap<>();

            log.info("Currency ID Controller: " + id);
            log.info("Currencies Controller: " + currency);
            response.put("Currency", currency);
            log.info("Response: " + response);
            return super.response(response);

        } catch (ServiceException e) {
            return super.internalError();
        }
    }
}
