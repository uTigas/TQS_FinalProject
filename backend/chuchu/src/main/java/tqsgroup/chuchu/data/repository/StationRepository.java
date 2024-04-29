package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    public Station findByName(String name);
}
