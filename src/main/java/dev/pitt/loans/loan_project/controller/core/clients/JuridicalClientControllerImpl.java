package dev.pitt.loans.loan_project.controller.core.clients;

import dev.pitt.loans.loan_project.controller.base.GenericController;
import dev.pitt.loans.loan_project.controller.commons.CustomResponse;
import dev.pitt.loans.loan_project.dto.request.JuridicalClientRequestDTO;
import dev.pitt.loans.loan_project.dto.response.JuridicalClientResponseDTO;
import dev.pitt.loans.loan_project.dto.response.JuridicalClientSunatResponseDTO;
import dev.pitt.loans.loan_project.exception.ControllerException;
import dev.pitt.loans.loan_project.exception.NotContentException;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.client.JuridicalClientService;
import dev.pitt.loans.loan_project.services.core.client.SunatFactoryService;
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
public class JuridicalClientControllerImpl extends GenericController
        implements JuridicalClientController {

    private final JuridicalClientService juridicalClientService;
    private final SunatFactoryService sunatFactoryService;

    @Override
    public ResponseEntity<CustomResponse> getCompanySunat(String ruc)
            throws ControllerException, NotContentException {
        try{
            Optional<JuridicalClientSunatResponseDTO> companySunat = sunatFactoryService.getFactorySunat(ruc);
            if(companySunat.isEmpty()) return super.notContent();
            Map<String, Object> response = new HashMap<>();
            response.put("ClientSunat", companySunat.get());
            return super.response(response);
        }catch (ServiceException e) {
            return super.internalError();
        }
    }

    @Override
    public ResponseEntity<CustomResponse> save(JuridicalClientRequestDTO juridicalClientRequestDTO,
                                               BindingResult result) throws ControllerException, NotContentException {
        if (result.hasErrors()) throw new ControllerException(result);

        try{

            JuridicalClientResponseDTO savedJuridicalClient = juridicalClientService.save(juridicalClientRequestDTO);

            EntityModel<JuridicalClientResponseDTO> resource = EntityModel.of(savedJuridicalClient,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(JuridicalClientController.class)
                                    .getById(savedJuridicalClient.getJuridicalClientId()))
                            .withSelfRel());
            Map<String, Object> response = new HashMap<>();
            response.put("JuridicalClient", savedJuridicalClient);
            response.put("link", resource.getLinks());

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
            response.put("JuridicalClient", juridicalClientService.findById(id));
            return super.response(response);
        }catch (ServiceException e) {
            return super.internalError();
        }

    }

    @Override
    public ResponseEntity<CustomResponse> getAll()
            throws ControllerException, NotContentException {
        try {
            List<JuridicalClientResponseDTO> juridicalClients = juridicalClientService.getAll();
            Map<String, Object> response = new HashMap<>();
            response.put("JuridicalClients", juridicalClients);
            response.put("cantResult", juridicalClients.size());

            return super.getResponse(response);
        } catch (ServiceException e) {
            return super.internalError();
        }
    }


}
