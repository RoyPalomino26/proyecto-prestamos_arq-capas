package dev.pitt.loans.loan_project.services.core.client;

import dev.pitt.loans.loan_project.dto.request.ClientDocumentRequestDTO;
import dev.pitt.loans.loan_project.dto.request.ClientPhoneRequestDTO;
import dev.pitt.loans.loan_project.dto.request.JuridicalClientRequestDTO;
import dev.pitt.loans.loan_project.dto.response.ClientDocumentResponseDTO;
import dev.pitt.loans.loan_project.dto.response.ClientPhoneResponseDTO;
import dev.pitt.loans.loan_project.dto.response.JuridicalClientResponseDTO;
import dev.pitt.loans.loan_project.dto.response.JuridicalClientSunatResponseDTO;
import dev.pitt.loans.loan_project.dto.response.LoanResponseDTO;
import dev.pitt.loans.loan_project.entity.core.oracle.client.JuridicalClientEntity;
import dev.pitt.loans.loan_project.entity.core.oracle.client.NaturalClientEntity;
import dev.pitt.loans.loan_project.entity.core.oracle.loan.LoanEntity;
import dev.pitt.loans.loan_project.entity.core.postgres.finance.InterestRateEntity;
import dev.pitt.loans.loan_project.entity.enums.ClientType;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.JuridicalClientRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.postgres.InterestRateRepository;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.client.details.ClientDocumentService;
import dev.pitt.loans.loan_project.services.core.client.details.ClientPhoneService;
import dev.pitt.loans.loan_project.services.integration.ws.external.sunat.client.SunatClient;
import dev.pitt.loans.loan_project.services.integration.ws.external.sunat.model.SunatClientModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JuridicalClientServiceImpl implements JuridicalClientService,
    SunatFactoryService{

    private final SunatClient sunatClient;
    private final JuridicalClientRepository juridicalClientRepository;
    private final ClientPhoneService clientPhoneService;
    private final ModelMapper modelMapperPhone;
    private final ModelMapper modelMapperDocument;
    private final InterestRateRepository interestRateRepository;
    private final ClientDocumentService clientDocumentService;

    public JuridicalClientServiceImpl(SunatClient sunatClient,
                                      JuridicalClientRepository juridicalClientRepository,
                                      ClientPhoneService clientPhoneService,
                                      @Qualifier("clientPhoneMapper") ModelMapper modelMapperPhone,
                                      @Qualifier("clientDocumentMapper") ModelMapper modelMapperDocument,
                                      InterestRateRepository interestRateRepository,
                                      ClientDocumentService clientDocumentService) {
        this.sunatClient = sunatClient;
        this.juridicalClientRepository = juridicalClientRepository;
        this.clientPhoneService = clientPhoneService;
        this.modelMapperPhone = modelMapperPhone;
        this.modelMapperDocument = modelMapperDocument;
        this.interestRateRepository = interestRateRepository;
        this.clientDocumentService = clientDocumentService;
    }

    @Value("${token.api}")
    private String tokenApi;

    @Override
    public Optional<JuridicalClientSunatResponseDTO> getFactorySunat(String ruc) {
        String auth = "Bearer "+tokenApi;
        SunatClientModel sunatClientModel = sunatClient.getCompanySunat(ruc, auth);

        if(sunatClientModel == null ||
                !sunatClientModel.getNumeroDocumento().equalsIgnoreCase(ruc)) return Optional.empty();

        JuridicalClientSunatResponseDTO juridicalClientSunatResponseDTO = JuridicalClientSunatResponseDTO.builder()
                .companyName(sunatClientModel.getRazonSocial())
                .ruc(sunatClientModel.getNumeroDocumento())
                .address(sunatClientModel.getDireccion())
                .district(sunatClientModel.getDistrito())
                .province(sunatClientModel.getProvincia())
                .department(sunatClientModel.getDepartamento())
                .typeJuridicalClient(sunatClientModel.getTipo())
                .economicActivity(sunatClientModel.getActividadEconomica())
                .build();

        return Optional.of(juridicalClientSunatResponseDTO);
    }

    @Override
    public JuridicalClientResponseDTO save(
            JuridicalClientRequestDTO juridicalCLientRequestDTO) throws ServiceException {

        JuridicalClientEntity juridicalClientEntity = getJuridicalClientEntity(juridicalCLientRequestDTO);
        JuridicalClientEntity juridicalClientEntitySaved = juridicalClientRepository.save(juridicalClientEntity);

        List<ClientPhoneRequestDTO> phoneRequestDTOs = Optional
                .ofNullable(juridicalCLientRequestDTO.getPhones())
                .orElse(Collections.emptyList());

        List<ClientDocumentRequestDTO> documentRequestDTOs = Optional
                .ofNullable(juridicalCLientRequestDTO.getDocuments())
                .orElse(Collections.emptyList());

        List<ClientPhoneResponseDTO> listClientPhoneResponse = savePhoneEntities(
                phoneRequestDTOs, juridicalClientEntitySaved);

        List<ClientDocumentResponseDTO> listDocumentResponse = saveDocumentEntities(
                documentRequestDTOs, juridicalClientEntitySaved);

        return getJuridicalClientResponseDTO(juridicalClientEntitySaved, listClientPhoneResponse, listDocumentResponse);
    }

    @Override
    public JuridicalClientResponseDTO findById(Long id) throws ServiceException {
        JuridicalClientEntity juridicalClientEntity = juridicalClientRepository.findById(id).orElseThrow(
                () -> new ServiceException("Juridical Client not found")
        );

        return getJuridicalClientResponseDTO(juridicalClientEntity);
    }

    @Override
    public List<JuridicalClientResponseDTO> getAll() throws ServiceException {
        List<JuridicalClientEntity> juridicalClientEntities = juridicalClientRepository.findAll();

        return Optional.ofNullable(juridicalClientEntities)
                .map(juridicalClients -> juridicalClients.stream()
                        .map(this::getJuridicalClientResponseDTO)
                        .toList())
                .orElse(Collections.emptyList());
    }

    private static JuridicalClientEntity getJuridicalClientEntity(
            JuridicalClientRequestDTO juridicalClientRequestDTO) {
        JuridicalClientEntity juridicalClientEntity = new JuridicalClientEntity();
        juridicalClientEntity.setRuc(juridicalClientRequestDTO.getRuc());
        juridicalClientEntity.setCompanyName(juridicalClientRequestDTO.getCompanyName());
        juridicalClientEntity.setLegalRepresentative(juridicalClientRequestDTO.getLegalRepresentative());
        juridicalClientEntity.setDistrict(juridicalClientRequestDTO.getDistrict());
        juridicalClientEntity.setProvince(juridicalClientRequestDTO.getProvince());
        juridicalClientEntity.setDepartment(juridicalClientRequestDTO.getDepartment());
        juridicalClientEntity.setTypeJuridicalClient(juridicalClientRequestDTO.getTypeJuridicalClient());
        juridicalClientEntity.setActivityEconomic(juridicalClientRequestDTO.getActivityEconomic());
        juridicalClientEntity.setClientType(ClientType.JURIDICA);
        return juridicalClientEntity;
    }

    private static JuridicalClientResponseDTO getJuridicalClientResponseDTO(JuridicalClientEntity juridicalClientEntitySaved,
                                                                          List<ClientPhoneResponseDTO> listClientPhoneResponse,
                                                                            List<ClientDocumentResponseDTO> listDocumentResponse) {
        JuridicalClientResponseDTO juridicalClientResponseDTO = new JuridicalClientResponseDTO();
        juridicalClientResponseDTO.setJuridicalClientId(juridicalClientEntitySaved.getClientId());
        juridicalClientResponseDTO.setRuc(juridicalClientEntitySaved.getRuc());
        juridicalClientResponseDTO.setCompanyName(juridicalClientEntitySaved.getCompanyName());
        juridicalClientResponseDTO.setLegalRepresentative(juridicalClientEntitySaved.getLegalRepresentative());
        juridicalClientResponseDTO.setAddress(juridicalClientEntitySaved.getAddress());
        juridicalClientResponseDTO.setEmail(juridicalClientEntitySaved.getEmail());
        juridicalClientResponseDTO.setDistrict(juridicalClientEntitySaved.getDistrict());
        juridicalClientResponseDTO.setProvince(juridicalClientEntitySaved.getProvince());
        juridicalClientResponseDTO.setDepartment(juridicalClientEntitySaved.getDepartment());
        juridicalClientResponseDTO.setTypeJuridicalClient(juridicalClientEntitySaved.getTypeJuridicalClient());
        juridicalClientResponseDTO.setPhones(listClientPhoneResponse);
        juridicalClientResponseDTO.setDocuments(listDocumentResponse);
        juridicalClientResponseDTO.setLoans(Collections.emptyList());
        return juridicalClientResponseDTO;

    }

    private JuridicalClientResponseDTO getJuridicalClientResponseDTO(JuridicalClientEntity juridicalClientEntityFound){

        List<ClientPhoneResponseDTO> listClientPhoneResponse = Optional.ofNullable(juridicalClientEntityFound.getPhones())
                .map(phones -> phones.stream()
                        .map(phone -> modelMapperPhone.map(phone, ClientPhoneResponseDTO.class))
                        .toList())
                .orElse(Collections.emptyList());

        List<ClientDocumentResponseDTO> listClientDocumentResponse = Optional.ofNullable(juridicalClientEntityFound.getDocuments())
                .map(documents -> documents.stream()
                        .map(document -> modelMapperDocument.map(document, ClientDocumentResponseDTO.class))
                        .toList())
                .orElse(Collections.emptyList());

        List<LoanResponseDTO> listLoanResponse = Optional.ofNullable(juridicalClientEntityFound.getLoans())
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

        JuridicalClientResponseDTO juridicalClientResponseDTO = new JuridicalClientResponseDTO();
        juridicalClientResponseDTO.setJuridicalClientId(juridicalClientEntityFound.getClientId());
        juridicalClientResponseDTO.setRuc(juridicalClientEntityFound.getRuc());
        juridicalClientResponseDTO.setCompanyName(juridicalClientEntityFound.getCompanyName());
        juridicalClientResponseDTO.setLegalRepresentative(juridicalClientEntityFound.getLegalRepresentative());
        juridicalClientResponseDTO.setAddress(juridicalClientEntityFound.getAddress());
        juridicalClientResponseDTO.setEmail(juridicalClientEntityFound.getEmail());
        juridicalClientResponseDTO.setDistrict(juridicalClientEntityFound.getDistrict());
        juridicalClientResponseDTO.setProvince(juridicalClientEntityFound.getProvince());
        juridicalClientResponseDTO.setDepartment(juridicalClientEntityFound.getDepartment());
        juridicalClientResponseDTO.setTypeJuridicalClient(juridicalClientEntityFound.getTypeJuridicalClient());
        juridicalClientResponseDTO.setPhones(listClientPhoneResponse);
        juridicalClientResponseDTO.setDocuments(listClientDocumentResponse);
        juridicalClientResponseDTO.setLoans(listLoanResponse);
        return juridicalClientResponseDTO;
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

    private List<ClientPhoneResponseDTO> savePhoneEntities(List<ClientPhoneRequestDTO> phoneRequestDTOs,
                                                           JuridicalClientEntity juridicalClientEntity) throws ServiceException {
        List<ClientPhoneResponseDTO> savedPhoneEntities = new ArrayList<>();
        if(phoneRequestDTOs.isEmpty()) return savedPhoneEntities;

        for (ClientPhoneRequestDTO phoneRequestDTO : phoneRequestDTOs) {
            phoneRequestDTO.setClientId(juridicalClientEntity.getClientId());
            phoneRequestDTO.setTypeClient(ClientType.JURIDICA);
            ClientPhoneResponseDTO clientPhoneEntity = clientPhoneService.phoneRegister(phoneRequestDTO);
            savedPhoneEntities.add(clientPhoneEntity);
        }
        return savedPhoneEntities;
    }

    private List<ClientDocumentResponseDTO> saveDocumentEntities(List<ClientDocumentRequestDTO> documentRequestDTOs,
                                                           JuridicalClientEntity juridicalClientEntity) throws ServiceException {
        List<ClientDocumentResponseDTO> savedDocumentsEntities = new ArrayList<>();
        if(documentRequestDTOs.isEmpty()) return savedDocumentsEntities;

        for (ClientDocumentRequestDTO documentRequestDTO : documentRequestDTOs) {
            documentRequestDTO.setClientId(juridicalClientEntity.getClientId());
            documentRequestDTO.setClientType(ClientType.JURIDICA);
            ClientDocumentResponseDTO clientDocumentEntity = clientDocumentService.save(documentRequestDTO);
            savedDocumentsEntities.add(clientDocumentEntity);
        }
        return savedDocumentsEntities;
    }

}
