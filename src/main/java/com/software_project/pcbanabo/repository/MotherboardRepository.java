package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Motherboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface MotherboardRepository extends JpaRepository<Motherboard, Long>, JpaSpecificationExecutor<Motherboard> {
    List<Motherboard> findBySocket(String socket);
    List<Motherboard> findByFormFactor(String formFactor);
    List<Motherboard> findBySocketAndFormFactor(String socket, String formFactor);
}
