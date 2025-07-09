package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.SavedBuild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedBuildRepository extends JpaRepository<SavedBuild, Integer> {
    List<SavedBuild> findByIsPublicTrue();
    List<SavedBuild> findByUserId(Integer userId);
}
