package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Ram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RamRepository extends JpaRepository<Ram, Long>, JpaSpecificationExecutor<Ram> {
    List<Ram> findByMemType(String memType);
}
