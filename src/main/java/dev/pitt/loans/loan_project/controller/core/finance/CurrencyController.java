package dev.pitt.loans.loan_project.controller.core.finance;

import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.CurrencyRequestDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static dev.pitt.loans.loan_project.controller.constants.ApiEndPointsConstant.API_CURRENCY;

@RequestMapping(API_CURRENCY)
public interface CurrencyController {

    @PostMapping
    ResponseEntity<CustomResponse> save(
            @Valid @RequestBody CurrencyRequestDTO currencyRequestDTO,
            BindingResult result)throws ControllerException, NotContentException;

    @GetMapping
    ResponseEntity<CustomResponse> getAll() throws ControllerException, NotContentException;

    @GetMapping("/{id}")
    ResponseEntity<CustomResponse> getById(@PathVariable(value = "id") Long id)
            throws ControllerException, NotContentException;
}
