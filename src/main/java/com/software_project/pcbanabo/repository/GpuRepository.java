package com.software_project.pcbanabo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.software_project.pcbanabo.model.Gpu;

@Repository
public interface GpuRepository extends JpaRepository<Gpu, Long>, JpaSpecificationExecutor<Gpu> {
    List<Gpu> findByCardLengthLessThanEqual(int cardLength);
    Optional<Gpu> findByModelName(String modelName);
}
