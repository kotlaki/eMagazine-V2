package com.kurganov.serverdb.repositories;

import com.kurganov.serverdb.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findOneByName(String theRoleName);

    List<Role> findAll();
}
