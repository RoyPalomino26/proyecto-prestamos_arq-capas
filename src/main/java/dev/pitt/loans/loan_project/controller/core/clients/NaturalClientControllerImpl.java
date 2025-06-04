package dev.pitt.loans.loan_project.controller.core.clients;

import dev.pitt.loans.loan_project.controller.base.GenericController;
import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.controller.core.employee.AnalystController;
import dev.pitt.loans.loan_project.dto.request.NaturalClientRequestDTO;
import dev.pitt.loans.loan_project.dto.response.AnalystResponseDTO;
import dev.pitt.loans.loan_project.dto.response.NaturalClientReniecResponseDTO;
import dev.pitt.loans.loan_project.dto.response.NaturalClientResponseDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.client.NaturalClientService;
import dev.pitt.loans.loan_project.services.core.client.ReniecPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dev.pitt.loans.loan_project.controller.constants.ApiMessageConstant.API_MSG_RESPONSE_INVALID_ID;

@RequiredArgsConstructor
@RestController
public class NaturalClientControllerImpl extends GenericController implements NaturalClientController {

    private final NaturalClientService naturalClientService;
    private final ReniecPersonService reniecPersonService;

    @Override
    public ResponseEntity<CustomResponse> getPersonReniec(String dni)
            throws ControllerException, NotContentException {

        try {

            Optional<NaturalClientReniecResponseDTO> clientReniec = reniecPersonService.getPersonReniec(dni);
            if (clientReniec.isEmpty()) return super.notContent();
            Map<String, Object> response = new HashMap<>();
            response.put("ClientReniec", clientReniec.get());
            return super.response(response);
        } catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> save(
            NaturalClientRequestDTO naturalClientRequestDTO,
            BindingResult result) throws ControllerException, NotContentException {
        if (result.hasErrors()) throw new ControllerException(result);

        try{

            NaturalClientResponseDTO savedNaturalClient = naturalClientService.save(naturalClientRequestDTO);

            EntityModel<NaturalClientResponseDTO> resource = EntityModel.of(savedNaturalClient,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NaturalClientController.class)
                                    .getById(savedNaturalClient.getNaturalClientId()))
                            .withSelfRel());

            Map<String, Object> response = new HashMap<>();
            response.put("Natural Client", savedNaturalClient);
            response.put("links", resource.getLinks());

            return super.created(response);

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
            response.put("Natural Client", naturalClientService.findById(id));
            return super.response(response);

        }catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> getAll() throws ControllerException, NotContentException {
        try{
            List<NaturalClientResponseDTO> naturalClients = naturalClientService.getAll();
            Map<String, Object> response = new HashMap<>();
            response.put("Natural Clients", naturalClients);
            response.put("cantResults", naturalClients.size());
            return super.response(response);
        } catch (ServiceException e) {
            return super.internalError();
        }
    }


}
