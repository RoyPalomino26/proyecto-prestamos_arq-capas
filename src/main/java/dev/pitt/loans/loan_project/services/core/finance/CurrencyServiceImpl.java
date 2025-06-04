package dev.pitt.loans.loan_project.services.core.finance;

import dev.pitt.loans.loan_project.dto.request.CurrencyRequestDTO;
import dev.pitt.loans.loan_project.dto.response.CurrencyResponseDTO;
import dev.pitt.loans.loan_project.entity.core.postgres.finance.CurrencyEntity;
import dev.pitt.loans.loan_project.repository.jpa.core.postgres.CurrencyRepository;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final ModelMapper modelMapper;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository,
                               @Qualifier("defaultMapper") ModelMapper modelMapper) {
        this.currencyRepository = currencyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CurrencyResponseDTO save(CurrencyRequestDTO currencyRequestDTO) throws ServiceException {
        CurrencyEntity currencyEntity = modelMapper.map(currencyRequestDTO, CurrencyEntity.class);
        return modelMapper.map(currencyRepository.save(currencyEntity), CurrencyResponseDTO.class);
    }

    @Override
    public CurrencyResponseDTO findById(Long id) throws ServiceException {
        CurrencyEntity currencyEntity = currencyRepository.findById(id).orElseThrow(
                () -> new ServiceException("Currency not found")
        );
        log.info("Currency found Service: {}", currencyEntity);
        log.info("Currency found: {}", currencyEntity.getName());
        return modelMapper.map(currencyEntity, CurrencyResponseDTO.class);
    }

    @Override
    public List<CurrencyResponseDTO> getAll() throws ServiceException {
        List<CurrencyEntity> currencyEntities = currencyRepository.findAll();
        return currencyEntities.stream()
                .map(currencyEntity ->
                        modelMapper.map(currencyEntity, CurrencyResponseDTO.class))
                .toList();
    }

    @Override
    public CurrencyResponseDTO update(CurrencyRequestDTO currencyRequestDTO, Long id) throws ServiceException {
        CurrencyEntity currencyEntity = currencyRepository.findById(id).orElseThrow(
                () -> new ServiceException("Currency not found")
        );

        currencyEntity.setName(currencyRequestDTO.getName());
        currencyEntity.setSymbol(currencyRequestDTO.getSymbol());
        currencyEntity.setCodeIso(currencyRequestDTO.getCodeIso());

        return modelMapper.map(currencyRepository.save(currencyEntity), CurrencyResponseDTO.class);
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        CurrencyEntity currencyEntity = currencyRepository.findById(id).orElseThrow(
                () -> new ServiceException("Currency not found")
        );
        currencyRepository.delete(currencyEntity);
        return true;
    }

}
