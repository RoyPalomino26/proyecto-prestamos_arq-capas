package dev.pitt.loans.loan_project.services.core.loan;

import dev.pitt.loans.loan_project.dto.request.ClientDocumentRequestDTO;
import dev.pitt.loans.loan_project.dto.request.LoanApplicationRequestDTO;
import dev.pitt.loans.loan_project.dto.request.LoanProcessRequestDTO;
import dev.pitt.loans.loan_project.dto.request.LoanSimulationRequestDTO;
import dev.pitt.loans.loan_project.dto.response.LoanReportResponseDTO;
import dev.pitt.loans.loan_project.dto.response.LoanResponseDTO;
import dev.pitt.loans.loan_project.entity.base.ClientEntity;
import dev.pitt.loans.loan_project.entity.core.oracle.loan.LoanEntity;
import dev.pitt.loans.loan_project.entity.core.oracle.loan.LoanTrackingEntity;
import dev.pitt.loans.loan_project.entity.core.postgres.employee.AnalystEntity;
import dev.pitt.loans.loan_project.entity.core.postgres.finance.CurrencyEntity;
import dev.pitt.loans.loan_project.entity.core.postgres.finance.InterestRateEntity;
import dev.pitt.loans.loan_project.entity.enums.ClientType;
import dev.pitt.loans.loan_project.entity.enums.LoanProcessStatus;
import dev.pitt.loans.loan_project.entity.enums.LoanStage;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.JuridicalClientRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.LoanRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.LoanTrackingRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.oracle.NaturalClientRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.postgres.AnalystRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.postgres.CurrencyRepository;
import dev.pitt.loans.loan_project.repository.jpa.core.postgres.InterestRateRepository;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import dev.pitt.loans.loan_project.services.core.client.details.ClientDocumentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanSimulatorService,
        LoanRequestedService, LoanService, LoanProcessingService{

    private final LoanRepository loanRepository;
    private final CurrencyRepository currencyRepository;
    private final InterestRateRepository interestRateRepository;
    private final ClientDocumentService clientDocumentService;
    private final LoanTrackingRepository loanTrackingRepository;
    private final AnalystRepository analystRepository;
    private final JuridicalClientRepository juridicalClientRepository;
    private final NaturalClientRepository naturalClientRepository;


    public LoanServiceImpl(LoanRepository loanRepository, CurrencyRepository currencyRepository,
                           InterestRateRepository interestRateRepository, ClientDocumentService clientDocumentService,
                           LoanTrackingRepository loanTrackingRepository, AnalystRepository analystRepository,
                           JuridicalClientRepository juridicalClientRepository, NaturalClientRepository naturalClientRepository) {
        this.loanRepository = loanRepository;
        this.currencyRepository = currencyRepository;
        this.interestRateRepository = interestRateRepository;
        this.clientDocumentService = clientDocumentService;
        this.loanTrackingRepository = loanTrackingRepository;
        this.analystRepository = analystRepository;
        this.juridicalClientRepository = juridicalClientRepository;
        this.naturalClientRepository = naturalClientRepository;
    }


    @Override
    public LoanResponseDTO simulateLoan(LoanSimulationRequestDTO loanSimulationRequestDTO) throws ServiceException {
        CurrencyEntity currencyEntity = currencyRepository.
                findById(loanSimulationRequestDTO.getCurrencyId())
                .orElseThrow(() -> new ServiceException("Currency not found"));
        InterestRateEntity interestRateEntity = interestRateRepository
                .findById(loanSimulationRequestDTO.getInterestRateId())
                .orElseThrow(() -> new ServiceException("Interest rate not found"));

        BigDecimal interestRate = interestRateEntity.getRateValue();
        BigDecimal monthlyPayment = calculateMonthlyPayment(BigDecimal.valueOf(loanSimulationRequestDTO.getAmount()),
                interestRate, loanSimulationRequestDTO.getTermMonths());

        loanSimulationRequestDTO.setMonthlyPayment(monthlyPayment);
        LoanEntity loanEntity = getLoanEntity(loanSimulationRequestDTO);
        loanEntity.setStage(LoanStage.SIMULATION);
        loanEntity.setStatus(LoanProcessStatus.SIMULATION);
        loanEntity.setCurrencyId(currencyEntity.getCurrencyId());
        loanEntity.setInterestRateId(interestRateEntity.getInterestRateId());

        LoanEntity loanEntitySaved = loanRepository.save(loanEntity);

        return getLoanResponseDTO(loanEntitySaved);
    }

    @Transactional
    @Override
    public LoanResponseDTO requestLoan(LoanApplicationRequestDTO loanApplicationRequestDTO) throws ServiceException {
        CurrencyEntity currencyEntity = currencyRepository.
                findById(loanApplicationRequestDTO.getCurrencyId())
                .orElseThrow(() -> new ServiceException("Currency not found"));
        InterestRateEntity interestRateEntity = interestRateRepository
                .findById(loanApplicationRequestDTO.getInterestRateId())
                .orElseThrow(() -> new ServiceException("Interest rate not found"));

        LoanEntity loanEntity = getLoanEntity(loanApplicationRequestDTO);
        loanEntity.setStage(LoanStage.LOAN);
        loanEntity.setStatus(LoanProcessStatus.LOAN_REQUESTED);
        loanEntity.setCurrencyId(currencyEntity.getCurrencyId());
        loanEntity.setInterestRateId(interestRateEntity.getInterestRateId());

        LoanEntity loanEntitySaved = loanRepository.save(loanEntity);
        savedDocuments(loanApplicationRequestDTO.getClientDocuments());

        return getLoanResponseDTO(loanEntitySaved);
    }

    @Transactional
    @Override
    public void reviewLoan(LoanProcessRequestDTO loanProcessRequestDTO, Long loandId) throws ServiceException {
        LoanEntity loanEntity = loanRepository.findById(loandId)
                .orElseThrow(() -> new ServiceException("Loan not found"));

        AnalystEntity analystEntity = analystRepository.findById(loanProcessRequestDTO.getAnalystId())
                .orElseThrow(() -> new ServiceException("Analyst not found"));

        // Actualizar el estado del préstamo
        loanEntity.setStatus(LoanProcessStatus.UNDER_REVIEW);
        // Guardar el préstamo actualizado
        loanRepository.save(loanEntity);

        // Crear una nueva entrada de seguimiento del préstamo
        LoanTrackingEntity loanTrackingEntity = new LoanTrackingEntity();
        loanTrackingEntity.setLoan(loanEntity);
        loanTrackingEntity.setAnalystId(analystEntity.getAnalystId());
        loanTrackingEntity.setObservations(loanProcessRequestDTO.getObservations());
        loanTrackingEntity.setStatus(LoanProcessStatus.UNDER_REVIEW);

        // Guardar la entrada de seguimiento del préstamo
        loanTrackingRepository.save(loanTrackingEntity);
    }

    @Transactional
    @Override
    public void approveLoan(LoanProcessRequestDTO loanProcessRequestDTO, Long loandId) throws ServiceException {
        LoanEntity loanEntity = loanRepository.findById(loandId)
                .orElseThrow(() -> new ServiceException("Loan not found"));

        AnalystEntity analystEntity = analystRepository.findById(loanProcessRequestDTO.getAnalystId())
                .orElseThrow(() -> new ServiceException("Analyst not found"));

        // Actualizar el estado del préstamo
        loanEntity.setStatus(LoanProcessStatus.APPROVED);
        // Guardar el préstamo actualizado
        loanRepository.save(loanEntity);

        // Crear una nueva entrada de seguimiento del préstamo
        LoanTrackingEntity loanTrackingEntity = new LoanTrackingEntity();
        loanTrackingEntity.setLoan(loanEntity);
        loanTrackingEntity.setAnalystId(analystEntity.getAnalystId());
        loanTrackingEntity.setObservations(loanProcessRequestDTO.getObservations());
        loanTrackingEntity.setStatus(LoanProcessStatus.APPROVED);

        // Guardar la entrada de seguimiento del préstamo
        loanTrackingRepository.save(loanTrackingEntity);

    }

    @Transactional
    @Override
    public void rejectLoan(LoanProcessRequestDTO loanProcessRequestDTO, Long loandId) throws ServiceException {
        LoanEntity loanEntity = loanRepository.findById(loandId)
                .orElseThrow(() -> new ServiceException("Loan not found"));

        AnalystEntity analystEntity = analystRepository.findById(loanProcessRequestDTO.getAnalystId())
                .orElseThrow(() -> new ServiceException("Analyst not found"));

        // Actualizar el estado del préstamo
        loanEntity.setStatus(LoanProcessStatus.REJECTED);
        // Guardar el préstamo actualizado
        loanRepository.save(loanEntity);

        // Crear una nueva entrada de seguimiento del préstamo
        LoanTrackingEntity loanTrackingEntity = new LoanTrackingEntity();
        loanTrackingEntity.setLoan(loanEntity);
        loanTrackingEntity.setAnalystId(analystEntity.getAnalystId());
        loanTrackingEntity.setObservations(loanProcessRequestDTO.getObservations());
        loanTrackingEntity.setStatus(LoanProcessStatus.REJECTED);

        // Guardar la entrada de seguimiento del préstamo
        loanTrackingRepository.save(loanTrackingEntity);

    }

    @Override
    public LoanResponseDTO findById(Long id) throws ServiceException {
        LoanEntity loanEntity = loanRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Loan not found"));

        return getLoanResponseDTO(loanEntity);
    }


    private BigDecimal calculateMonthlyPayment(BigDecimal principal, BigDecimal annualInterestRate, int termMonths) {
        if (annualInterestRate.compareTo(BigDecimal.ZERO) <= 0 || termMonths <= 0) {
            throw new IllegalArgumentException("Interest rate and term must be greater than zero");
        }

        BigDecimal monthlyInterestRate = annualInterestRate.divide(BigDecimal.valueOf(12), MathContext.DECIMAL128);
        BigDecimal onePlusRateToPower = monthlyInterestRate.add(BigDecimal.ONE).pow(termMonths, MathContext.DECIMAL128);

        BigDecimal numerator = principal.multiply(monthlyInterestRate).multiply(onePlusRateToPower);
        BigDecimal denominator = onePlusRateToPower.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, MathContext.DECIMAL128);
    }

    private  LoanEntity getLoanEntity(LoanSimulationRequestDTO loanSimulationRequestDTO)throws ServiceException{

        ClientEntity clientEntity = findClientEntity(loanSimulationRequestDTO.getClientId(),
                loanSimulationRequestDTO.getClientType());

        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setClient(clientEntity);
        loanEntity.setAmount(BigDecimal.valueOf(loanSimulationRequestDTO.getAmount()));
        loanEntity.setInterestRateId(loanSimulationRequestDTO.getInterestRateId());
        loanEntity.setCurrencyId(loanSimulationRequestDTO.getCurrencyId());
        loanEntity.setTermMonths(loanSimulationRequestDTO.getTermMonths());
        loanEntity.setMonthlyPayment(loanSimulationRequestDTO.getMonthlyPayment());
        loanEntity.setLoanTrackings(null);

        return loanEntity;
    }

    private  LoanEntity getLoanEntity(LoanApplicationRequestDTO loanApplicationRequestDTO)throws ServiceException{
        ClientEntity clientEntity = findClientEntity(loanApplicationRequestDTO.getClientId(),
                loanApplicationRequestDTO.getClientType());
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setClient(clientEntity);
        loanEntity.setAmount(BigDecimal.valueOf(loanApplicationRequestDTO.getAmount()));
        loanEntity.setInterestRateId(loanApplicationRequestDTO.getInterestRateId());
        loanEntity.setCurrencyId(loanApplicationRequestDTO.getCurrencyId());
        loanEntity.setTermMonths(loanApplicationRequestDTO.getTermMonths());
        loanEntity.setMonthlyPayment(loanApplicationRequestDTO.getMonthlyPayment());
        loanEntity.setLoanTrackings(null);

        return loanEntity;
    }

    private  LoanResponseDTO getLoanResponseDTO(LoanEntity loanEntitySaved) throws ServiceException{
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

    private void savedDocuments(List<ClientDocumentRequestDTO> clientDocumentRequestDTOList)  {
        if (!(clientDocumentRequestDTOList == null) && !(clientDocumentRequestDTOList.isEmpty())){
            for (ClientDocumentRequestDTO clientDocumentRequestDTO : clientDocumentRequestDTOList){
                try {
                    clientDocumentService.save(clientDocumentRequestDTO);
                } catch (ServiceException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
