package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Ssd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SsdRepository extends JpaRepository<Ssd, Long>, JpaSpecificationExecutor<Ssd> {
    // Additional query methods can be defined here if needed
}
