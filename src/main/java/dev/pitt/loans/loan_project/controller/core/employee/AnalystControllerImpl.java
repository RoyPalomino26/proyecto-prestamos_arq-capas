package dev.pitt.loans.loan_project.controller.core.employee;

import dev.pitt.loans.loan_project.controller.base.GenericController;
import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.AnalystRequestDTO;
import dev.pitt.loans.loan_project.dto.response.AnalystResponseDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.employee.AnalystService;
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
public class AnalystControllerImpl extends GenericController implements AnalystController{

    private final AnalystService analystService;

    @Override
    public ResponseEntity<CustomResponse> save(AnalystRequestDTO analystRequestDTO,
                                               BindingResult result) throws ControllerException, NotContentException {
        if (result.hasErrors()) throw new ControllerException(result);

        try {

            AnalystResponseDTO savedAnalyst = analystService.save(analystRequestDTO);

            EntityModel<AnalystResponseDTO> resource = EntityModel.of(savedAnalyst,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AnalystController.class)
                                    .getById(savedAnalyst.getAnalystId()))
                            .withSelfRel());

            Map<String, Object> response = new HashMap<>();
            response.put("Analyst",savedAnalyst);
            response.put("links",resource.getLinks());


            return super.created(response);
        } catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> getAll()
            throws ControllerException, NotContentException {
        try {
            List<AnalystResponseDTO> analysts = analystService.getAll();

            //if (analysts.isEmpty())  throw new NotContentException();

            Map<String, Object> response = new HashMap<>();
            response.put("Analysts", analysts);
            response.put("cantResult", analysts.size());

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
            Map<String, Object> response = new HashMap<>();
            response.put("Analysts", analystService.findById(id));

            return super.response(response);
        } catch (ServiceException e) {
            return super.internalError();
        }
    }
}
