package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Benchmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BenchmarkRepository extends JpaRepository<Benchmark, Long>, JpaSpecificationExecutor<Benchmark> {
    Benchmark findByCpuId(Long cpuId);
    Benchmark findByGpuId(Long gpuId);
}
