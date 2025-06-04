package dev.pitt.loans.loan_project.controller.core.employee;


import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.AnalystRequestDTO;
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

import static dev.pitt.loans.loan_project.controller.constants.ApiEndPointsConstant.API_ANALYST;


@RequestMapping(API_ANALYST)
public interface AnalystController {

        @PostMapping
        ResponseEntity<CustomResponse>save(
                @Valid @RequestBody AnalystRequestDTO analystRequestDTO,
                BindingResult result) throws ControllerException, NotContentException;

        @GetMapping
        ResponseEntity<CustomResponse> getAll() throws ControllerException, NotContentException;

        @GetMapping("/{id}")
        ResponseEntity<CustomResponse> getById(@PathVariable(value = "id") Long id)
                throws ControllerException, NotContentException;
}
