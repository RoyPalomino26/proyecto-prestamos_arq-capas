package dev.pitt.loans.loan_project.controller.core.clients.details;

import dev.pitt.loans.loan_project.controller.base.GenericController;
import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.ClientDocumentRequestDTO;
import dev.pitt.loans.loan_project.dto.response.ClientDocumentResponseDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.client.details.ClientDocumentService;
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
public class ClientDocumentControllerImpl extends GenericController implements ClientDocumentController {

    private final ClientDocumentService clientDocumentService;

    @Override
    public ResponseEntity<CustomResponse> save(
            ClientDocumentRequestDTO clientDocumentRequestDTO,
            BindingResult result) throws ControllerException, NotContentException {
        if (result.hasErrors()) throw new ControllerException(result);

        try {
            ClientDocumentResponseDTO savedClientDocument =
                    clientDocumentService.save(clientDocumentRequestDTO);

            EntityModel<ClientDocumentResponseDTO> resource = EntityModel.of(savedClientDocument,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientDocumentController.class)
                            .getById(savedClientDocument.getClientId()))
                            .withSelfRel());

            Map<String, Object> response = new HashMap<>();
            response.put("Client Document", savedClientDocument);
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
            List<ClientDocumentResponseDTO> allClientDocument = clientDocumentService.getAll();

            Map<String, Object> response = new HashMap<>();
            response.put("Client Document", allClientDocument);
            response.put("cantResult", allClientDocument.size());

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
            response.put("Client Document", clientDocumentService.findById(id));
            return super.getResponse(response);
        }catch (ServiceException e) {
            return super.internalError();}
        }
}
