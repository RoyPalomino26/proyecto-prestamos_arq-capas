package dev.pitt.loans.loan_project.services.core.finance;

import dev.pitt.loans.loan_project.dto.request.InterestRateRequestDTO;
import dev.pitt.loans.loan_project.dto.response.InterestRateResponseDTO;
import dev.pitt.loans.loan_project.entity.core.postgres.finance.InterestRateEntity;
import dev.pitt.loans.loan_project.repository.jpa.core.postgres.InterestRateRepository;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestRateServiceImpl implements InterestRateService {

    private final InterestRateRepository interestRateRepository;
    private final ModelMapper modelMapper;

    public InterestRateServiceImpl(InterestRateRepository interestRateRepository,
                                   @Qualifier("defaultMapper") ModelMapper modelMapper) {
        this.interestRateRepository = interestRateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public InterestRateResponseDTO save(InterestRateRequestDTO interestRateRequestDTO)
            throws ServiceException {
        InterestRateEntity interestRateEntity = modelMapper.map(interestRateRequestDTO, InterestRateEntity.class);
        return modelMapper.map(interestRateRepository.save(interestRateEntity), InterestRateResponseDTO.class);
    }

    @Override
    public InterestRateResponseDTO findById(Long id) throws ServiceException {
        InterestRateEntity interestRateEntity = interestRateRepository.findById(id).orElseThrow(
                () -> new ServiceException("Interest Rate not found")
        );
        return modelMapper.map(interestRateEntity, InterestRateResponseDTO.class);
    }

    @Override
    public List<InterestRateResponseDTO> getAll() throws ServiceException {
        List<InterestRateEntity> interestRateEntities = interestRateRepository.findAll();
        return interestRateEntities.stream()
                .map(interestRateEntity ->
                        modelMapper.map(interestRateEntity, InterestRateResponseDTO.class))
                .toList();
    }

    @Override
    public InterestRateResponseDTO update(InterestRateRequestDTO interestRateRequestDTO, Long id)
            throws ServiceException {
        InterestRateEntity interestRateEntity = interestRateRepository.findById(id).orElseThrow(
                () -> new ServiceException("Interest Rate not found")
        );

        interestRateEntity.setName(interestRateRequestDTO.getName());
        interestRateEntity.setRateValue(interestRateRequestDTO.getRateValue());
        interestRateEntity.setValidFrom(interestRateRequestDTO.getValidFrom());
        interestRateEntity.setValidTo(interestRateRequestDTO.getValidTo());

        return modelMapper.map(interestRateRepository.save(interestRateEntity), InterestRateResponseDTO.class);
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        InterestRateEntity interestRateEntity = interestRateRepository.findById(id).orElseThrow(
                () -> new ServiceException("Interest Rate not found")
        );
        interestRateRepository.delete(interestRateEntity);
        return true;
    }
}
