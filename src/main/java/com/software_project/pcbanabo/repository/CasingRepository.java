package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Casing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasingRepository extends JpaRepository<Casing, Long> {
    List<Casing> findByMotherboardSupport(String motherboardSupport);
    List<Casing> findByMotherboardSupportAndPsuClearanceGreaterThanEqualAndGpuClearanceGreaterThanEqualAndCpuClearanceGreaterThanEqual(String motherboardSupport, Integer psuClearance, Integer gpuClearance, Integer cpuClearance);
}
