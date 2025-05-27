package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Ram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RamRepository extends JpaRepository<Ram, Long> {
    List<Ram> findByMemType(String memType);
}
