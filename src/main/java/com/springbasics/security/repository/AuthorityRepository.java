package com.springbasics.security.repository;

import com.springbasics.security.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    Authority findByroleCode(String roleCode);
    Boolean existsByRoleCode(String roleCode);
}
