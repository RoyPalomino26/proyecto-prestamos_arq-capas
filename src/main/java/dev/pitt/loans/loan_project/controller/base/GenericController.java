package dev.pitt.loans.loan_project.controller.base;

import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.controller.commons.ResponseMetadata;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static dev.pitt.loans.loan_project.controller.constants.ApiMessageConstant.API_MSG_RESPONSE_INTERNAL_ERROR;
import static dev.pitt.loans.loan_project.controller.constants.ApiMessageConstant.API_MSG_RESPONSE_QUERY_SUCCESS;
import static dev.pitt.loans.loan_project.controller.constants.ApiMessageConstant.API_MSG_RESPONSE_REGISTRATION_SUCCESS;
import static dev.pitt.loans.loan_project.utils.UtilValidator.validId;
import static java.util.Objects.isNull;

public abstract class GenericController {

    protected ResponseEntity<CustomResponse> internalError() {
        CustomResponse customResponse = CustomResponse.builder()
                .metadata(buildMetadata(
                        API_MSG_RESPONSE_INTERNAL_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR)
                )
                .build();
        return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<CustomResponse> response(Object res) {
        if (isNull(res)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CustomResponse customResponse = CustomResponse.builder()
                .metadata(buildMetadata(
                        API_MSG_RESPONSE_QUERY_SUCCESS,
                        HttpStatus.OK)
                )
                .data(Optional.of(res))
                .build();
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    protected ResponseEntity<CustomResponse> getResponse(Map<String, Object> mapResponse) throws NotContentException {
        if (mapResponse == null || mapResponse.isEmpty()) {
            throw new NotContentException();
        }

        CustomResponse customResponse = CustomResponse.builder()
                .metadata(buildMetadata(
                        API_MSG_RESPONSE_QUERY_SUCCESS,
                        HttpStatus.OK)
                )
                .data(Optional.of(mapResponse))
                .build();
        return ResponseEntity.ok(customResponse);
    }

    protected ResponseEntity<CustomResponse> notContent() {
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    protected ResponseEntity<CustomResponse> created(Object data) {

        CustomResponse customResponse = CustomResponse.builder()
                .metadata(buildMetadata(
                        API_MSG_RESPONSE_REGISTRATION_SUCCESS,
                        HttpStatus.CREATED))
                .data(Optional.of(data))
                .build();
        return new ResponseEntity<>(customResponse,HttpStatus.CREATED);

    }


    protected ResponseEntity<CustomResponse> badRequest(String message/*, Object data*/) {

        CustomResponse customResponse = CustomResponse.builder()
                //.message(API_MSG_RESPONSE_REGISTRO_ERROR)
                .metadata(buildMetadata(
                        API_MSG_RESPONSE_REGISTRATION_SUCCESS,
                        HttpStatus.BAD_REQUEST))
                .build();

        return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);

    }

    protected Boolean ValidIdGen(Long id) {
        return validId(id);
    }

    protected ControllerException getException(Exception e) {
        return new ControllerException(e);
    }

    private ResponseMetadata buildMetadata(String message, HttpStatus status) {
        return ResponseMetadata.builder()
                .message(message)
                .httpCode(status.value() + "-" + status.name())
                .dataTime(LocalDateTime.now().toString())
                .build();
    }
}
