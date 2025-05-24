package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.CpuCooler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuCoolerRepository extends JpaRepository<CpuCooler, Long> {
}
