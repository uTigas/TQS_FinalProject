package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByRole(String role);
}