package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Motherboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotherboardRepository extends JpaRepository<Motherboard, Long> {
    List<Motherboard> findBySocket(String socket);
    List<Motherboard> findByFormFactor(String formFactor);
    List<Motherboard> findBySocketAndFormFactor(String socket, String formFactor);
}
