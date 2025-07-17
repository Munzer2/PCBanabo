package com.software_project.pcbanabo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software_project.pcbanabo.repository.CpuReviewRepository;


@Service
public class CpuReviewService {
    private final CpuReviewRepository cpuReviewRepository;

    @Autowired
    public CpuReviewService(CpuReviewRepository cpuReviewRepository) {
        this.cpuReviewRepository = cpuReviewRepository;
    } 
}
