package nc.unc.smartContractBailRural.controller.services;

import nc.unc.smartContractBailRural.model.Point;
import nc.unc.smartContractBailRural.model.SmartContract;
import nc.unc.smartContractBailRural.smartContractBailRural.BailRural;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EtherumServices {

    private static final String RPC_SERVER_URL = "http://localhost:7545";

    /*
    clé privée ganache = 0xaa796895cb9678d17b5d46434b7bdded2e20dd1b6251094e8e309fcc57fe8a1e
     */
    private static final String PRIVATE_KEY = "0xaa796895cb9678d17b5d46434b7bdded2e20dd1b6251094e8e309fcc57fe8a1e";

    /*
    adresse du compte = 0x278EAEb5cBE6bedDe54Aa730fFF10E38801F4Ed6
     */
    private static final String CONTRACT_OWNER_ADDRESS = "0x278EAEb5cBE6bedDe54Aa730fFF10E38801F4Ed6";

    private Web3j web3j;

    private Credentials credentials;

    public EtherumServices() {
        this.web3j = Web3j.build(new HttpService(RPC_SERVER_URL));
        this.credentials = Credentials.create(PRIVATE_KEY);
    }

    public void sendSmartContract(SmartContract smartContract) throws Exception {
        BailRural contract = BailRural.deploy(
                web3j,
                credentials,
                ManagedTransaction.GAS_PRICE,
                Contract.GAS_LIMIT
        ).send();
        System.out.println("Contract deployed at: " + contract.getContractAddress());
        List<BigInteger> data = smartContract.getPoints().stream().map(Point::getLatitude).collect(Collectors.toList());
        data.addAll(smartContract.getPoints().stream().map(Point::getLongitude).collect(Collectors.toList()));
        contract.recordLand(smartContract.getProprietaire(),smartContract.getAres(),data).send();
    }
}
