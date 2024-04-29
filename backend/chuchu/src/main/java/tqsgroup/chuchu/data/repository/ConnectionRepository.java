package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    
}
