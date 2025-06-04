package dev.pitt.loans.loan_project.services.core.client;

import dev.pitt.loans.loan_project.dto.request.ClientDocumentRequestDTO;
import dev.pitt.loans.loan_project.dto.request.ClientPhoneRequestDTO;
import dev.pitt.loans.loan_project.dto.request.NaturalClientRequestDTO;
import dev.pitt.loans.loan_project.dto.response.ClientDocumentResponseDTO;
import dev.pitt.loans.loan_project.dto.response.ClientPhoneResponseDTO;
import dev.pitt.loans.loan_project.dto.response.LoanResponseDTO;
import dev.pitt.loans.loan_project.dto.response.NaturalClientReniecResponseDTO;
import dev.pitt.loans.loan_project.dto.response.NaturalClientResponseDTO;
import dev.pitt.loans.loan_project.entity.core.oracle.client.NaturalClientEntity;
import dev.pitt.loans.loan_project.entity.core.oracle.loan.LoanEntity;
import dev.pitt.loans.loan_project.entity.core.postgres.finance.InterestRateEntity;
import dev.pitt.loans.loan_project.entity.enums.ClientType;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.NaturalClientRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.postgres.InterestRateRepository;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.client.details.ClientDocumentService;
import dev.pitt.loans.loan_project.services.core.client.details.ClientPhoneService;
import dev.pitt.loans.loan_project.services.integration.ws.external.reniec.client.ReniecClient;
import dev.pitt.loans.loan_project.services.integration.ws.external.reniec.model.ReniecClientModel;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NaturalClientServiceImpl implements NaturalClientService,
        ReniecPersonService {

    private final ReniecClient reniecClient;
    private final NaturalClientRepository naturalClientRepository;
    private final ClientPhoneService clientPhoneService;
    private final ModelMapper modelMapperPhone;
    private final ModelMapper modelMapperDocument;
    private final InterestRateRepository interestRateRepository;
    private final ClientDocumentService clientDocumentService;

    public NaturalClientServiceImpl(ReniecClient reniecClient,
                                    NaturalClientRepository naturalClientRepository,
                                    ClientPhoneService clientPhoneService,
                                    @Qualifier("clientPhoneMapper") ModelMapper modelMapperPhone,
                                    @Qualifier("clientDocumentMapper") ModelMapper modelMapperDocument,
                                    InterestRateRepository interestRateRepository,
                                    ClientDocumentService clientDocumentService) {
        this.reniecClient = reniecClient;
        this.naturalClientRepository = naturalClientRepository;
        this.clientPhoneService = clientPhoneService;
        this.modelMapperPhone = modelMapperPhone;
        this.modelMapperDocument = modelMapperDocument;
        this.interestRateRepository = interestRateRepository;
        this.clientDocumentService = clientDocumentService;
    }

    @Value("${token.api}")
    private String tokenApi;

    @Override
    public Optional<NaturalClientReniecResponseDTO> getPersonReniec(String dni) throws ServiceException {
        String auth = "Bearer "+tokenApi;
        ReniecClientModel reniecClientModel = reniecClient.getPersonReniec(dni, auth);

        log.info("Reniec Client Model: {}", reniecClientModel.getNombres());

        if(reniecClientModel == null ||
                !reniecClientModel.getNumeroDocumento().equalsIgnoreCase(dni)) return Optional.empty();


        NaturalClientReniecResponseDTO naturalClientReniecResponseDTO = NaturalClientReniecResponseDTO.builder()
                .names(reniecClientModel.getNombres())
                .lastName(reniecClientModel.getApellidoPaterno()+" "+reniecClientModel.getApellidoMaterno())
                .dni(reniecClientModel.getNumeroDocumento())
                .build();
        log.info("Reniec Client Response: {}", naturalClientReniecResponseDTO.getNames());
        return Optional.of(naturalClientReniecResponseDTO);

    }

    @Transactional
    @Override
    public NaturalClientResponseDTO save(NaturalClientRequestDTO naturalClientRequestDTO)
            throws ServiceException {
        NaturalClientEntity naturalClientEntity = getNaturalClientEntity(naturalClientRequestDTO);

        NaturalClientEntity naturalClientEntitySaved = naturalClientRepository.save(naturalClientEntity);

        List<ClientPhoneRequestDTO> phoneRequestDTOs = Optional
                .ofNullable(naturalClientRequestDTO.getPhones())
                .orElse(Collections.emptyList());

        List<ClientDocumentRequestDTO> documentRequestDTOs = Optional
                .ofNullable(naturalClientRequestDTO.getDocuments())
                .orElse(Collections.emptyList());

        List<ClientPhoneResponseDTO> listClientPhoneResponse = savePhoneEntities(
                phoneRequestDTOs, naturalClientEntitySaved);

        List<ClientDocumentResponseDTO> listClientDocumentResponse = saveDocumentEntities(
        documentRequestDTOs, naturalClientEntitySaved);

        return getNaturalClientResponseDTO(naturalClientEntitySaved, listClientPhoneResponse, listClientDocumentResponse);
    }

    @Override
    public NaturalClientResponseDTO findById(Long id) throws ServiceException {
        NaturalClientEntity naturalClientEntity = naturalClientRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Client not found"));

        return getNaturalClientResponseDTO(naturalClientEntity);
    }

    @Override
    public List<NaturalClientResponseDTO> getAll() throws ServiceException {
        List<NaturalClientEntity> naturalClientEntities = naturalClientRepository.findAll();

        return Optional.ofNullable(naturalClientEntities)
                .map(naturalClients -> naturalClients.stream()
                        .map(this::getNaturalClientResponseDTO)
                        .toList())
                .orElse(Collections.emptyList());
    }

    private List<ClientPhoneResponseDTO> savePhoneEntities(List<ClientPhoneRequestDTO> phoneRequestDTOs,
                                                           NaturalClientEntity naturalClientEntitySaved) throws ServiceException {
        List<ClientPhoneResponseDTO> savedPhoneEntities = new ArrayList<>();

        if(phoneRequestDTOs.isEmpty()) return savedPhoneEntities;

        for (ClientPhoneRequestDTO phoneRequestDTO : phoneRequestDTOs) {
            phoneRequestDTO.setClientId(naturalClientEntitySaved.getClientId());
            phoneRequestDTO.setTypeClient(ClientType.NATURAL);
            ClientPhoneResponseDTO clientPhoneEntity = clientPhoneService.phoneRegister(phoneRequestDTO);
            savedPhoneEntities.add(clientPhoneEntity);
        }

        return savedPhoneEntities;
    }

    private static NaturalClientEntity getNaturalClientEntity(NaturalClientRequestDTO clientRequestDTO) {
        NaturalClientEntity naturalClient = new NaturalClientEntity();
        naturalClient.setDni(clientRequestDTO.getDni());
        naturalClient.setNames(clientRequestDTO.getNames());
        naturalClient.setLastName(clientRequestDTO.getLastName());
        naturalClient.setOcupation(clientRequestDTO.getOcupation());
        naturalClient.setBirthDate(clientRequestDTO.getBirth());
        naturalClient.setAddress(clientRequestDTO.getAddress());
        naturalClient.setGender(clientRequestDTO.getGender());
        naturalClient.setEmail(clientRequestDTO.getEmail());
        naturalClient.setClientType(ClientType.NATURAL);
        return naturalClient;
    }

    private static NaturalClientResponseDTO getNaturalClientResponseDTO(NaturalClientEntity naturalClientEntitySaved,
                                                                        List<ClientPhoneResponseDTO> listClientPhoneResponse,
                                                                        List<ClientDocumentResponseDTO> listClientDocumentResponse) {
        NaturalClientResponseDTO naturalClientResponseDTO = new NaturalClientResponseDTO();
        naturalClientResponseDTO.setNaturalClientId(naturalClientEntitySaved.getClientId());
        naturalClientResponseDTO.setDni(naturalClientEntitySaved.getDni());
        naturalClientResponseDTO.setNames(naturalClientEntitySaved.getNames());
        naturalClientResponseDTO.setLastName(naturalClientEntitySaved.getLastName());
        naturalClientResponseDTO.setOcupation(naturalClientEntitySaved.getOcupation());
        naturalClientResponseDTO.setGender(naturalClientEntitySaved.getGender());
        naturalClientResponseDTO.setBirthDate(naturalClientEntitySaved.getBirthDate());
        naturalClientResponseDTO.setAddress(naturalClientEntitySaved.getAddress());
        naturalClientResponseDTO.setEmail(naturalClientEntitySaved.getEmail());
        naturalClientResponseDTO.setPhones(listClientPhoneResponse);
        naturalClientResponseDTO.setDocuments(listClientDocumentResponse);
        return naturalClientResponseDTO;
    }

    private NaturalClientResponseDTO getNaturalClientResponseDTO(NaturalClientEntity naturalClientEntityFound){
        List<ClientPhoneResponseDTO> listClientPhoneResponse = Optional.ofNullable(naturalClientEntityFound.getPhones())
                .map(phones -> phones.stream()
                        .map(phone -> modelMapperPhone.map(phone, ClientPhoneResponseDTO.class))
                        .toList())
                .orElse(Collections.emptyList());

        List<ClientDocumentResponseDTO> listClientDocumentResponse = Optional.ofNullable(naturalClientEntityFound.getDocuments())
                .map(documents -> documents.stream()
                        .map(document -> modelMapperDocument.map(document, ClientDocumentResponseDTO.class))
                        .toList())
                .orElse(Collections.emptyList());

        List<LoanResponseDTO> listLoanResponse = Optional.ofNullable(naturalClientEntityFound.getLoans())
                .map(loans -> loans.stream()
                        .map(loan -> {
                            try {
                                return getLoanResponseDTO(loan);
                            } catch (ServiceException e) {
                                e.printStackTrace();
                                return null;
                            }
                        })
                        .toList())
                .orElse(Collections.emptyList());

        NaturalClientResponseDTO naturalClientResponseDTO = new NaturalClientResponseDTO();
        naturalClientResponseDTO.setNaturalClientId(naturalClientEntityFound.getClientId());
        naturalClientResponseDTO.setDni(naturalClientEntityFound.getDni());
        naturalClientResponseDTO.setNames(naturalClientEntityFound.getNames());
        naturalClientResponseDTO.setLastName(naturalClientEntityFound.getLastName());
        naturalClientResponseDTO.setOcupation(naturalClientEntityFound.getOcupation());
        naturalClientResponseDTO.setGender(naturalClientEntityFound.getGender());
        naturalClientResponseDTO.setBirthDate(naturalClientEntityFound.getBirthDate());
        naturalClientResponseDTO.setAddress(naturalClientEntityFound.getAddress());
        naturalClientResponseDTO.setEmail(naturalClientEntityFound.getEmail());
        naturalClientResponseDTO.setPhones(listClientPhoneResponse);
        naturalClientResponseDTO.setDocuments(listClientDocumentResponse);
        naturalClientResponseDTO.setLoans(listLoanResponse);
        return naturalClientResponseDTO;
    }

    private LoanResponseDTO getLoanResponseDTO(LoanEntity loanEntitySaved) throws ServiceException{
        InterestRateEntity interestRateEntity = interestRateRepository
                .findById(loanEntitySaved.getInterestRateId())
                .orElseThrow(() -> new ServiceException("Interest rate not found"));
        return LoanResponseDTO.builder()
                .loanId(loanEntitySaved.getLoanId())
                .amount(loanEntitySaved.getAmount())
                .interestRate(interestRateEntity.getRateValue())
                .termMonths(loanEntitySaved.getTermMonths())
                .monthlyPayment(loanEntitySaved.getMonthlyPayment())
                .status(loanEntitySaved.getStatus())
                .build();
    }

    private List<ClientDocumentResponseDTO> saveDocumentEntities(
            List<ClientDocumentRequestDTO> documentRequestDTOs,
            NaturalClientEntity naturalClientEntitySaved) throws ServiceException {
        List<ClientDocumentResponseDTO> savedDocumentEntities = new ArrayList<>();

        if(documentRequestDTOs.isEmpty()) return savedDocumentEntities;

        for (ClientDocumentRequestDTO documentRequestDTO : documentRequestDTOs) {
            documentRequestDTO.setClientId(naturalClientEntitySaved.getClientId());
            documentRequestDTO.setClientType(ClientType.NATURAL);
            ClientDocumentResponseDTO clientDocumentEntity = clientDocumentService.save(documentRequestDTO);
            savedDocumentEntities.add(clientDocumentEntity);
        }

        return savedDocumentEntities;

    }

}
