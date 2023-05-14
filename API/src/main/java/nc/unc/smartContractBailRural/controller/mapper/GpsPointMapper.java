package nc.unc.smartContractBailRural.controller.mapper;

import nc.unc.smartContractBailRural.model.Point;
import nc.unc.smartContractBailRural.repository.jpa.GpsPointRepository;
import nc.unc.smartContractBailRural.controller.dto.GpsPointDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GpsPointMapper {

    @Autowired
    private GpsPointRepository gpsPointRepository;

    public GpsPointDto toDto(Point entity){
        return GpsPointDto.builder()
                .id(entity.getId())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }

    public Point toEntity(GpsPointDto dto){
        return Point.builder()
                .id(dto.getId())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }

    public List<GpsPointDto> toDtos(List<Point> entities){
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Point> toEntities(List<GpsPointDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
