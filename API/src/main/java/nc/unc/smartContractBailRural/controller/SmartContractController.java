package nc.unc.smartContractBailRural.controller;

import io.swagger.annotations.ApiOperation;
import nc.unc.smartContractBailRural.model.Point;
import nc.unc.smartContractBailRural.model.SmartContract;
import nc.unc.smartContractBailRural.repository.jpa.GpsPointRepository;
import nc.unc.smartContractBailRural.repository.jpa.SmartContractRepository;
import nc.unc.smartContractBailRural.controller.dto.GpsPointDto;
import nc.unc.smartContractBailRural.controller.dto.SmartContractDto;
import nc.unc.smartContractBailRural.controller.mapper.GpsPointMapper;
import nc.unc.smartContractBailRural.controller.mapper.SmartContractMapper;
import nc.unc.smartContractBailRural.controller.services.EtherumServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.transaction.Transactional;
import java.util.List;

@EnableSwagger2
@RestController
@Transactional
public class SmartContractController {

    @Autowired
    private SmartContractRepository smartContractRepository;

    @Autowired
    private GpsPointRepository gpsPointRepository;

    @Autowired
    private SmartContractMapper smartContractMapper;

    @Autowired
    private GpsPointMapper gpsPointMapper;

    @Autowired
    private EtherumServices etherumServices;

    @ApiOperation(value = "methode de test.")
    @GetMapping(value = "/helloWorld")
    public String helloWorld(){
        return "hello world !!!";
    }

    @ApiOperation(value = "historiques des baux ruraux.")
    @GetMapping(value = "/smartcontract/")
    public List<SmartContractDto> findAllSmartContract(){
        List<SmartContract> smartContracts = smartContractRepository.findAll();
        return smartContractMapper.toDtos(smartContracts);
    }

    @GetMapping(value = "/smartcontract/name=")
    @ApiOperation(value = "Bail rural du proprietaire.")
    public SmartContractDto findByProprietaire(@RequestParam String proprietaire){
        return smartContractMapper.toDto(smartContractRepository.findByProprietaire(proprietaire));
    }

    @ApiOperation(value = "Création d'un bail rural.")
    @PostMapping(value = "/smartContract/")
    public void createSmartContract(@RequestBody SmartContractDto smartContractDto) throws Exception {
        smartContractRepository.save(smartContractMapper.toEntity(smartContractDto));
        etherumServices.sendSmartContract(smartContractMapper.toEntity(smartContractDto));
    }
    @ApiOperation(value = "Création d'un point gps.")
    @PostMapping(value = "/gpsPoint/")
    public void createGpsPoint(@RequestBody GpsPointDto dto){ gpsPointRepository.save(gpsPointMapper.toEntity(dto)); }

    @ApiOperation(value = "Liste de points gps.")
    @GetMapping(value = "/gpsPoint/")
    public List<GpsPointDto> findAllGpsPoint(){
        List<Point> point = gpsPointRepository.findAll();
        return gpsPointMapper.toDtos(point);
    }
}
