package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Casing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasingRepository extends JpaRepository<Casing, Long> {
}
