package dev.pitt.loans.loan_project.config;

import dev.pitt.loans.loan_project.dto.request.AnalystRequestDTO;
import dev.pitt.loans.loan_project.dto.response.AnalystResponseDTO;
import dev.pitt.loans.loan_project.dto.request.ClientPhoneRequestDTO;
import dev.pitt.loans.loan_project.dto.response.ClientPhoneResponseDTO;
import dev.pitt.loans.loan_project.entity.core.oracle.client.details.ClientPhoneEntity;
import dev.pitt.loans.loan_project.entity.core.oracle.loan.LoanTrackingEntity;
import dev.pitt.loans.loan_project.entity.core.postgres.employee.AnalystEntity;
import dev.pitt.loans.loan_project.entity.enums.AnalystRole;
import dev.pitt.loans.loan_project.entity.enums.PhoneType;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class MapperConfig {

    @Bean(name = "defaultMapper")
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean(name = "analystMapper")
    public ModelMapper analystMapper(){
        ModelMapper modelMapper = new ModelMapper();
        //Definiendo una estrategia de Mapeo
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //Customizacion de mapeo ESCRITURA
        modelMapper.createTypeMap(AnalystRequestDTO.class, AnalystEntity.class)
                .addMapping(AnalystRequestDTO::getAnalystId, (dest, v) -> dest.setAnalystId((Long) v))
                .addMapping(AnalystRequestDTO::getFullNameAnalyst, (dest, v) -> dest.setFullName((String) v))
                .addMapping(AnalystRequestDTO::getEmailAnalyst, (dest, v) -> dest.setEmail((String) v))
                .addMapping(AnalystRequestDTO::getRoleAnalyst, (dest, v) -> dest.setRole((AnalystRole) v));

        //Customizacion de mapeo LECTURA
        modelMapper.createTypeMap(AnalystEntity.class, AnalystResponseDTO.class)
                .addMapping(AnalystEntity::getAnalystId, (dest, v) -> dest.setAnalystId((Long) v))
                .addMapping(AnalystEntity::getFullName, (dest, v) -> dest.setFullNameAnalyst((String) v))
                .addMapping(AnalystEntity::getEmail, (dest, v) -> dest.setEmailAnalyst((String) v))
                .addMapping(AnalystEntity::getRole, (dest, v) -> dest.setRole((AnalystRole) v))
                //private List<LoanTrackingEntity> loanTrackings   ->      private Object idsLoanTracking
                .addMapping(src -> {
                            if (src.getLoanTrackings() == null) {
                                return Collections.emptyList();
                            }
                            return src.getLoanTrackings().stream().map(LoanTrackingEntity::getLoanTrackingId).toList();
                        },
                        AnalystResponseDTO::setIdsLoanTracking);

        return modelMapper;
    }

    @Bean(name = "clientPhoneMapper")
    public ModelMapper clientPhoneMapper(){
        ModelMapper modelMapper = new ModelMapper();
        //Definiendo una estrategia de Mapeo
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //Customizacion de mapeo ESCRITURA
        modelMapper.createTypeMap(ClientPhoneRequestDTO.class, ClientPhoneEntity.class)
                .addMapping(ClientPhoneRequestDTO::getPhoneType,(dest, v) -> dest.setPhoneType((PhoneType) v))
                .addMapping(ClientPhoneRequestDTO::getPhoneNumber,(dest, v) -> dest.setPhoneNumber((String) v));

        //Customizacion de mapeo LECTURA
        modelMapper.createTypeMap(ClientPhoneEntity.class, ClientPhoneResponseDTO.class)
                .addMapping(ClientPhoneEntity::getPhoneId,(dest, v) -> dest.setPhoneId((Long) v))
                .addMapping(ClientPhoneEntity::getPhoneType,(dest, v) -> dest.setPhoneType((PhoneType) v))
                .addMapping(ClientPhoneEntity::getPhoneNumber,(dest, v) -> dest.setPhoneNumber((String) v))
                .addMapping(src -> src.getClient().getClientId(), ClientPhoneResponseDTO::setClientId);

        return modelMapper;
    }

    @Bean(name = "clientDocumentMapper")
    public ModelMapper clientDocumentMapper(){
        ModelMapper modelMapper = new ModelMapper();
        //Definiendo una estrategia de Mapeo
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //Customizacion de mapeo ESCRITURA
        modelMapper.createTypeMap(ClientPhoneRequestDTO.class, ClientPhoneEntity.class)
                .addMapping(ClientPhoneRequestDTO::getClientId,(dest, v) -> dest.getClient().setClientId((Long) v))
                .addMapping(ClientPhoneRequestDTO::getPhoneType,(dest, v) -> dest.setPhoneType((PhoneType) v))
                .addMapping(ClientPhoneRequestDTO::getPhoneNumber,(dest, v) -> dest.setPhoneNumber((String) v));

        //Customizacion de mapeo LECTURA
        modelMapper.createTypeMap(ClientPhoneEntity.class, ClientPhoneResponseDTO.class)
                .addMapping(ClientPhoneEntity::getPhoneId,(dest, v) -> dest.setPhoneId((Long) v))
                .addMapping(ClientPhoneEntity::getPhoneType,(dest, v) -> dest.setPhoneType((PhoneType) v))
                .addMapping(ClientPhoneEntity::getPhoneNumber,(dest, v) -> dest.setPhoneNumber((String) v))
                .addMapping(src -> src.getClient().getClientId(), ClientPhoneResponseDTO::setClientId);


        return modelMapper;
    }
  /*
    @Bean(name = "naturalClientMapper")
    public ModelMapper naturalClientMapper(){
        ModelMapper modelMapper = new ModelMapper();
        //Definiendo una estrategia de Mapeo
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //Customizacion de mapeo ESCRITURA
        modelMapper.createTypeMap(NaturalClientRequestDTO.class, NaturalClientEntity.class)
                .addMapping(NaturalClientRequestDTO::getDni,(dest, v) -> dest.setDni((String) v))
                .addMapping(NaturalClientRequestDTO::getNames,(dest, v) -> dest.setNames((String) v))
                .addMapping(NaturalClientRequestDTO::getLastName,(dest, v) -> dest.setLastName((String) v))
                .addMapping(NaturalClientRequestDTO::getOcupation,(dest, v) -> dest.setOcupation((String) v))
                .addMapping(NaturalClientRequestDTO::getBirth,(dest, v) -> dest.setBirthDate((LocalDateTime) v))
                .addMapping(NaturalClientRequestDTO::getAddress,(dest, v) -> dest.setAddress((String) v))
                .addMapping(NaturalClientRequestDTO::getGender,(dest, v) -> dest.setGender((Gender) v))
                .addMapping(NaturalClientRequestDTO::getEmail,(dest, v) -> dest.setEmail((String) v))
                .addMapping(NaturalClientRequestDTO::getPhones,(dest, v) -> dest.setPhones(Collections.emptyList()));



        return modelMapper;
    }

   */

    public <T, D> D mapItem(T item, Class<D> cl, ModelMapper modelMapper) {
        return modelMapper.map(item, cl);
    }

    /*
    public <T, D>List<D> map(List<T> list, Class<D> cl, ModelMapper modelMapper){
        return list.stream().map(item -> modelMapper.map(item, cl)).toList();
    }
     */
}
