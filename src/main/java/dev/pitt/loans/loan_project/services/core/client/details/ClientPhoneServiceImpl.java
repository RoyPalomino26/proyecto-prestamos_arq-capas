package dev.pitt.loans.loan_project.services.core.client.details;

import dev.pitt.loans.loan_project.dto.request.ClientPhoneRequestDTO;
import dev.pitt.loans.loan_project.dto.response.ClientPhoneResponseDTO;
import dev.pitt.loans.loan_project.entity.base.ClientEntity;
import dev.pitt.loans.loan_project.entity.core.oracle.client.details.ClientPhoneEntity;
import dev.pitt.loans.loan_project.entity.enums.ClientType;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.ClientPhoneRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.JuridicalClientRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.NaturalClientRepository;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.base.crud.GenericCrudService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClientPhoneServiceImpl implements ClientPhoneService {

    private final ClientPhoneRepository clientPhoneRepository;
    private final ModelMapper modelMapper;
    private final JuridicalClientRepository juridicalClientRepository;
    private final NaturalClientRepository naturalClientRepository;

    public ClientPhoneServiceImpl(ClientPhoneRepository clientPhoneRepository,
                                  @Qualifier("clientPhoneMapper") ModelMapper modelMapper,
                                  JuridicalClientRepository juridicalClientRepository,
                                  NaturalClientRepository naturalClientRepository) {
        this.clientPhoneRepository = clientPhoneRepository;
        this.modelMapper = modelMapper;
        this.juridicalClientRepository = juridicalClientRepository;
        this.naturalClientRepository = naturalClientRepository;
    }

    @Override
    public ClientPhoneResponseDTO findById(Long id) throws ServiceException {
        ClientPhoneEntity clientPhoneEntity = clientPhoneRepository.findById(id).orElseThrow(
                () -> new ServiceException("Client Phone not found")
        );
        return modelMapper.map(clientPhoneEntity, ClientPhoneResponseDTO.class);
    }

    @Override
    public List<ClientPhoneResponseDTO> getAll() throws ServiceException {
        List<ClientPhoneEntity> clientPhoneEntities = clientPhoneRepository.findAll();
        return clientPhoneEntities.stream()
                .map(clientPhoneEntity ->
                        modelMapper.map(clientPhoneEntity, ClientPhoneResponseDTO.class))
                .toList();
    }

    @Override
    public ClientPhoneResponseDTO phoneRegister(
            ClientPhoneRequestDTO clientPhoneRequestDTO) throws ServiceException {

        ClientPhoneEntity clientPhoneEntity = modelMapper.map(clientPhoneRequestDTO, ClientPhoneEntity.class);

        var clientEntity = findClientEntity(clientPhoneRequestDTO.getClientId(),
                clientPhoneRequestDTO.getTypeClient());

        clientPhoneEntity.setClient(clientEntity);
        log.info("Client Phone Entity: {}", clientPhoneEntity);
        log.info("Client Phone Entity - Cliente: {}", clientPhoneEntity.getClient());
        log.info("Client Phone Entity - Cliente: {}", clientPhoneEntity.getClient().getClientId());
        log.info("Client Phone Entity - Cliente: {}", clientPhoneEntity.getClient().getClientType());
        return modelMapper.map(clientPhoneRepository.save(clientPhoneEntity), ClientPhoneResponseDTO.class);
    }


    @Override
    public ClientPhoneResponseDTO update(
            ClientPhoneRequestDTO clientPhoneRequestDTO, Long id) throws ServiceException {

        ClientPhoneEntity clientPhoneEntity = clientPhoneRepository.findById(id).orElseThrow(
                () -> new ServiceException("Client Phone not found")
        );

        clientPhoneEntity.setPhoneType(clientPhoneRequestDTO.getPhoneType());
        clientPhoneEntity.setPhoneNumber(clientPhoneRequestDTO.getPhoneNumber());

        return modelMapper.map(clientPhoneRepository.save(clientPhoneEntity), ClientPhoneResponseDTO.class);
    }

    @Override
    public boolean delete(Long id) throws ServiceException {

        ClientPhoneEntity clientPhoneEntity = clientPhoneRepository.findById(id).orElseThrow(
                () -> new ServiceException("Client Phone not found")
        );

        clientPhoneRepository.delete(clientPhoneEntity);

        return true;
    }

    private ClientEntity findClientEntity(Long clientId, ClientType clientType) throws ServiceException {
        if (clientType == ClientType.NATURAL) {
            return naturalClientRepository.findById(clientId).orElseThrow(
                    () -> new ServiceException("Natural Client not found")
            );
        } else {
            return juridicalClientRepository.findById(clientId).orElseThrow(
                    () -> new ServiceException("Juridical Client not found")
            );
        }
    }
}
