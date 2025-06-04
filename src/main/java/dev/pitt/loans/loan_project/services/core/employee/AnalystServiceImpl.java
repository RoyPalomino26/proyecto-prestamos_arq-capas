package dev.pitt.loans.loan_project.services.core.employee;

import dev.pitt.loans.loan_project.dto.request.AnalystRequestDTO;
import dev.pitt.loans.loan_project.dto.response.AnalystResponseDTO;
import dev.pitt.loans.loan_project.entity.core.postgres.employee.AnalystEntity;
import dev.pitt.loans.loan_project.repository.jpa.core.postgres.AnalystRepository;
import dev.pitt.loans.loan_project.services.base.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalystServiceImpl implements AnalystService{

    private final AnalystRepository analystRepository;
    private final ModelMapper modelMapper;

    public AnalystServiceImpl(AnalystRepository analystRepository, @Qualifier("analystMapper") ModelMapper modelMapper) {
        this.analystRepository = analystRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AnalystResponseDTO save(AnalystRequestDTO analystRequestDTO) throws ServiceException {
       AnalystEntity analystEntity = analystRepository.save(modelMapper.map(analystRequestDTO, AnalystEntity.class));
        return modelMapper.map(analystEntity, AnalystResponseDTO.class);
    }

    @Override
    public AnalystResponseDTO findById(Long id) throws ServiceException {

        AnalystEntity analystEntity = analystRepository.findById(id).orElseThrow(
                () -> new ServiceException("Analyst not found")
        );

        // Codigo para obtener la informacion de los prestamos q esta en seguimiento
        /*
        * Code here
        *
        * */

        return modelMapper.map(analystEntity, AnalystResponseDTO.class);
    }

    @Override
    public List<AnalystResponseDTO> getAll() throws ServiceException {

        List<AnalystEntity> analystEntities = analystRepository.findAll();
        // Codigo para obtener la informacion de los prestamos q esta en seguimiento para todos los analistas
        /*
         * Code here
         *
         * */

        return analystEntities.stream()
                .map(analystEntity ->
                        modelMapper.map(analystEntity, AnalystResponseDTO.class))
                .toList();
    }

    @Override
    public AnalystResponseDTO update(AnalystRequestDTO analystRequestDTO, Long id) throws ServiceException {

        AnalystEntity analystEntity = analystRepository.findById(id).orElseThrow(
                () -> new ServiceException("Analyst not found")
        );

        analystEntity.setAnalystId(analystRequestDTO.getAnalystId());
        analystEntity.setFullName(analystRequestDTO.getFullNameAnalyst());
        analystEntity.setEmail(analystRequestDTO.getEmailAnalyst());
        analystEntity.setRole(analystRequestDTO.getRoleAnalyst());
        //Codigo para actualizar la informacion de los prestamos q esta en seguimiento
        /*
         * Code here
         *
         * */

        return modelMapper.map(analystRepository.save(analystEntity), AnalystResponseDTO.class);
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        AnalystEntity analystEntity = analystRepository.findById(id).orElseThrow(
                () -> new ServiceException("Analyst not found")
        );
        analystRepository.delete(analystEntity);
        return true;
    }
}
