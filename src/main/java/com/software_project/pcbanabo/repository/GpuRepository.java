package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Gpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpuRepository extends JpaRepository<Gpu, Long> {
}
