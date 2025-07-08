package com.software_project.pcbanabo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.software_project.pcbanabo.model.Casing;

@Repository
public interface CasingRepository extends JpaRepository<Casing, Long>, JpaSpecificationExecutor<Casing> {
    List<Casing> findByMotherboardSupport(String motherboardSupport);
    List<Casing> findByMotherboardSupportAndPsuClearanceGreaterThanEqualAndGpuClearanceGreaterThanEqualAndCpuClearanceGreaterThanEqual(String motherboardSupport, Integer psuClearance, Integer gpuClearance, Integer cpuClearance);
    Optional<Casing> findByModelName(String modelName);
}
