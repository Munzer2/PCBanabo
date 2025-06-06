package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Psu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsuRepository extends JpaRepository<Psu, Long> {
    List<Psu> findByWattageGreaterThanEqual(Integer wattage);
    List<Psu> findByPsuLengthLessThanEqual(Integer psuLength);
}
