package nc.unc.smartContractBailRural.repository.jpa;

import nc.unc.smartContractBailRural.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpsPointRepository extends JpaRepository<Point, Long> {
}
