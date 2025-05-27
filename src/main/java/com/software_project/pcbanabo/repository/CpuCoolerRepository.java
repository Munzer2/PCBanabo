package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.CpuCooler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CpuCoolerRepository extends JpaRepository<CpuCooler, Long> {
    List<CpuCooler> findByTowerHeightLessThanEqual(Integer towerHeight);
    List<CpuCooler> findByCoolingCapacityIsGreaterThanEqual(Integer coolingCapacity);
}
