package dev.pitt.loans.loan_project.controller.core.clients.details;

import dev.pitt.loans.loan_project.controller.base.GenericController;
import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.ClientPhoneRequestDTO;
import dev.pitt.loans.loan_project.dto.response.ClientPhoneResponseDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.client.details.ClientPhoneService;
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
public class ClientPhoneControllerImpl extends GenericController implements ClientPhoneController {

    private final ClientPhoneService clientPhoneService;

    @Override
    public ResponseEntity<CustomResponse> save(ClientPhoneRequestDTO clientPhoneRequestDTO,
                                               BindingResult result) throws ControllerException, NotContentException {
        if (result.hasErrors()) throw new ControllerException(result);

        try{
            ClientPhoneResponseDTO savedClientPhone = clientPhoneService.phoneRegister(clientPhoneRequestDTO);

            EntityModel<ClientPhoneResponseDTO> resource = EntityModel.of(savedClientPhone,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientPhoneController.class)
                            .getById(savedClientPhone.getClientId()))
                            .withSelfRel());
            Map<String, Object> response = new HashMap<>();
            response.put("Client Phone", savedClientPhone);
            response.put("links", resource.getLinks());

            return super.created(response);
        }
        catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> getAll() throws ControllerException, NotContentException {
        try{
            List<ClientPhoneResponseDTO> allClientPhone = clientPhoneService.getAll();

            Map<String, Object> response = new HashMap<>();
            response.put("Client Phone", allClientPhone);
            response.put("cantResult", allClientPhone.size());

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
            Map<String, Object> response = new HashMap<>();
            response.put("Client Phone", clientPhoneService.findById(id));

            return super.response(response);
        }catch (ServiceException e) {
            return super.internalError();
        }
    }
}
