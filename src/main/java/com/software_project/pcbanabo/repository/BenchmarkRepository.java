package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Benchmark;
import com.software_project.pcbanabo.model.BenchmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BenchmarkRepository extends JpaRepository<Benchmark, BenchmarkId>, JpaSpecificationExecutor<Benchmark> {
    Benchmark findByCpuIdAndGpuId(Long cpuId, Long gpuId);
}
