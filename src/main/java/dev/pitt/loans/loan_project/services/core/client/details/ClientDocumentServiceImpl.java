package dev.pitt.loans.loan_project.services.core.client.details;

import dev.pitt.loans.loan_project.dto.request.ClientDocumentRequestDTO;
import dev.pitt.loans.loan_project.dto.response.ClientDocumentResponseDTO;
import dev.pitt.loans.loan_project.entity.base.ClientEntity;
import dev.pitt.loans.loan_project.entity.core.oracle.client.details.ClientDocumentEntity;
import dev.pitt.loans.loan_project.entity.enums.ClientType;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.ClientDocumentRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.JuridicalClientRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.NaturalClientRepository;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientDocumentServiceImpl implements ClientDocumentService {

    private final ModelMapper modelMapper;
    private final ClientDocumentRepository clientDocumentRepository;
    private final JuridicalClientRepository juridicalClientRepository;
    private final NaturalClientRepository naturalClientRepository;

    public ClientDocumentServiceImpl(@Qualifier("clientDocumentMapper") ModelMapper modelMapper,
                                     ClientDocumentRepository clientDocumentRepository,
                                     JuridicalClientRepository juridicalClientRepository,
                                     NaturalClientRepository naturalClientRepository) {
        this.modelMapper = modelMapper;
        this.clientDocumentRepository = clientDocumentRepository;
        this.juridicalClientRepository = juridicalClientRepository;
        this.naturalClientRepository = naturalClientRepository;
    }

    @Override
    public ClientDocumentResponseDTO save(
            ClientDocumentRequestDTO clientDocumentRequestDTO) throws ServiceException {

        ClientDocumentEntity clientDocumentEntity = modelMapper.map(clientDocumentRequestDTO, ClientDocumentEntity.class);

        ClientEntity clientEntity = findClientEntity(clientDocumentRequestDTO.getClientId(),
                clientDocumentRequestDTO.getClientType());

        clientDocumentEntity.setClient(clientEntity);

        return modelMapper.map(clientDocumentRepository.save(clientDocumentEntity),
                ClientDocumentResponseDTO.class);
    }

    @Override
    public ClientDocumentResponseDTO findById(Long id)
            throws ServiceException {
        ClientDocumentEntity clientDocumentEntity = clientDocumentRepository.findById(id).orElseThrow(
                () -> new ServiceException("Client Document not found")
        );

        return modelMapper.map(clientDocumentEntity, ClientDocumentResponseDTO.class);
    }

    @Override
    public List<ClientDocumentResponseDTO> getAll() throws ServiceException {
        List<ClientDocumentEntity> clientDocumentEntities = clientDocumentRepository.findAll();
        return clientDocumentEntities.stream()
                .map(clientDocumentEntity ->
                        modelMapper.map(clientDocumentEntity, ClientDocumentResponseDTO.class))
                .toList();
    }


    @Override
    public ClientDocumentResponseDTO update(
            ClientDocumentRequestDTO clientDocumentRequestDTO, Long id) throws ServiceException {
        ClientDocumentEntity clientDocumentEntity = clientDocumentRepository.findById(id).orElseThrow(
                () -> new ServiceException("Client Document not found")
        );

        clientDocumentEntity.setDocument_type(clientDocumentRequestDTO.getDocument_type());
        //todo: hallar la url del documento

        return modelMapper.map(clientDocumentRepository.save(clientDocumentEntity),
                ClientDocumentResponseDTO.class);
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        ClientDocumentEntity clientDocumentEntity = clientDocumentRepository.findById(id).orElseThrow(
                () -> new ServiceException("Client Document not found")
        );
        clientDocumentRepository.delete(clientDocumentEntity);
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
