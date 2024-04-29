package tqsgroup.chuchu.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
}