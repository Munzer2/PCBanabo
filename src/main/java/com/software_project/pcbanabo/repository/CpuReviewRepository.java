package com.software_project.pcbanabo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.software_project.pcbanabo.model.CpuReview;

@Repository
public interface CpuReviewRepository extends JpaRepository<CpuReview, Long>, JpaSpecificationExecutor<CpuReview>{
    
}
