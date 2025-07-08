package com.software_project.pcbanabo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.software_project.pcbanabo.model.Cpu;

@Repository
public interface CpuRepository extends JpaRepository<Cpu, Long>, JpaSpecificationExecutor<Cpu> {
    Optional<Cpu> findByModelName(String modelName);
}
