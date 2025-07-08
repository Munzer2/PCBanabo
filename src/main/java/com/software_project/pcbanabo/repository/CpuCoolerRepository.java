package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.CpuCooler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface CpuCoolerRepository extends JpaRepository<CpuCooler, Long>, JpaSpecificationExecutor<CpuCooler> {
    List<CpuCooler> findByTowerHeightLessThanEqual(Integer towerHeight);
    List<CpuCooler> findByCoolingCapacityIsGreaterThanEqual(Integer coolingCapacity);
    List<CpuCooler> findByTowerHeight(Integer towerHeight);
    List<CpuCooler> findByCoolingCapacity(Integer coolingCapacity);
}
