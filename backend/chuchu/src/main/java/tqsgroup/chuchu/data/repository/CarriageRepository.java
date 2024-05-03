package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Carriage;
import tqsgroup.chuchu.data.entity.CarriageType;
import tqsgroup.chuchu.data.entity.Train;

import java.util.List;

@Repository
public interface CarriageRepository extends JpaRepository<Carriage, Long>{
    List<Carriage> findAllByTrain(Train train);
    Carriage findByType(CarriageType type);
}
