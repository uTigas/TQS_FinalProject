package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Carriage;

@Repository
public interface CarriageRepository extends JpaRepository<Carriage, Long>{
    
}
