package nc.unc.smartContractBailRural.controller.mapper;

import nc.unc.smartContractBailRural.model.SmartContract;
import nc.unc.smartContractBailRural.controller.dto.SmartContractDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SmartContractMapper {

    @Autowired
    private GpsPointMapper gpsPointMapper;

    public SmartContractDto toDto(SmartContract entity){
        return SmartContractDto.builder()
                .proprietaire(entity.getProprietaire())
                .ares(entity.getAres())
                .gpsPoint(gpsPointMapper.toDtos(entity.getPoints()))
                .build();
    }

    public SmartContract toEntity(SmartContractDto dto){
        return SmartContract.builder()
                .proprietaire(dto.getProprietaire())
                .ares(dto.getAres())
                .points(gpsPointMapper.toEntities(dto.getGpsPoint()))
                .build();
    }

    public List<SmartContractDto> toDtos(List<SmartContract> entities){
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

}
