package com.software_project.pcbanabo.service;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.software_project.pcbanabo.dto.SaveBuildRequestDTO;
import com.software_project.pcbanabo.model.SavedBuild;
import com.software_project.pcbanabo.repository.CasingRepository;
import com.software_project.pcbanabo.repository.CpuCoolerRepository;
import com.software_project.pcbanabo.repository.CpuRepository;
import com.software_project.pcbanabo.repository.GpuRepository;
import com.software_project.pcbanabo.repository.MotherboardRepository;
import com.software_project.pcbanabo.repository.PsuRepository;
import com.software_project.pcbanabo.repository.RamRepository;
import com.software_project.pcbanabo.repository.SavedBuildRepository;
import com.software_project.pcbanabo.repository.SsdRepository;

@Service
public class SavedBuildService {

    private final SavedBuildRepository savedBuildRepository;
    private final CpuRepository cpuRepository;
    private final CasingRepository casingRepository;
    private final CpuCoolerRepository cpuCoolerRepository;
    private final GpuRepository gpuRepository;
    private final MotherboardRepository motherboardRepository;
    private final PsuRepository psuRepository;
    private final RamRepository ramRepository;
    private final SsdRepository ssdRepository;
    
    public SavedBuildService(
            SavedBuildRepository savedBuildRepository,
            CpuRepository cpuRepository,
            CasingRepository casingRepository,
            CpuCoolerRepository cpuCoolerRepository,
            GpuRepository gpuRepository,
            MotherboardRepository motherboardRepository,
            PsuRepository psuRepository,
            RamRepository ramRepository,
            SsdRepository ssdRepository
    ) {
        this.savedBuildRepository = savedBuildRepository;
        this.cpuRepository = cpuRepository;
        this.casingRepository = casingRepository;
        this.cpuCoolerRepository = cpuCoolerRepository;
        this.gpuRepository = gpuRepository;
        this.motherboardRepository = motherboardRepository;
        this.psuRepository = psuRepository;
        this.ramRepository = ramRepository;
        this.ssdRepository = ssdRepository;
    }

    public List<SavedBuild> getPublicBuilds() {
        return savedBuildRepository.findByIsPublicTrue();
    }

    public SavedBuild getBuildById(Integer id) {
        return savedBuildRepository.findById(id).orElse(null);
    }

    public List<SavedBuild> getPublicBuildsFilteredByPrice(Integer minPrice, Integer maxPrice) {
        List<SavedBuild> allPublicBuilds = savedBuildRepository.findByIsPublicTrue();

        return allPublicBuilds.stream()
                .filter(build -> {
                    int totalPrice = calculateTotalBuildPrice(build);
                    boolean meetsMin = (minPrice == null || totalPrice >= minPrice);
                    boolean meetsMax = (maxPrice == null || totalPrice <= maxPrice);
                    return meetsMin && meetsMax;
                })
                .toList();
    }

    private int calculateTotalBuildPrice(SavedBuild build) {
        int total = 0;

        total += getComponentPrice(cpuRepository, build.getCpuId());
        total += getComponentPrice(gpuRepository, build.getGpuId());
        total += getComponentPrice(motherboardRepository, build.getMotherboardId());
        total += getComponentPrice(ramRepository, build.getRamId());
        total += getComponentPrice(ssdRepository, build.getSsdId());
        total += getComponentPrice(psuRepository, build.getPsuId());
        total += getComponentPrice(casingRepository, build.getCasingId());
        total += getComponentPrice(cpuCoolerRepository, build.getCpuCoolerId());

        return total;
    }

    private <T> int getComponentPrice(JpaRepository<T, Long> repo, Integer id) {
        if (id == null) return 0;
        try {
            Method getPrice = Objects.requireNonNull(repo.findById(Long.valueOf(id)).orElse(null))
                    .getClass().getMethod("getAvgPrice");
            return (int) getPrice.invoke(repo.findById(Long.valueOf(id)).orElse(null));
        } catch (Exception e) {
            return 0; // If no record or error, skip price
        }
    }

    public SavedBuild saveBuild(int userId, SaveBuildRequestDTO dto) {
        SavedBuild build = new SavedBuild();

        build.setUserId(userId);
        build.setCpuId(dto.getCpuId());
        build.setMotherboardId(dto.getMotherboardId());
        build.setRamId(dto.getRamId());
        build.setSsdId(dto.getSsdId());
        build.setGpuId(dto.getGpuId());
        build.setPsuId(dto.getPsuId());
        build.setCasingId(dto.getCasingId());
        build.setCpuCoolerId(dto.getCpuCoolerId());

        build.setBuildName(dto.getBuildName());
        build.setPublic(dto.getPublic() != null ? dto.getPublic() : false);
        build.setSavedAt(LocalDateTime.now());

        return savedBuildRepository.save(build);
    }

    public List<SavedBuild> getUserBuilds(Integer userId) {
        return savedBuildRepository.findByUserId(userId);
    }

    public boolean deleteBuild(Integer buildId) {
        try {
            if (savedBuildRepository.existsById(buildId)) {
                savedBuildRepository.deleteById(buildId);
                return true;
            }
            return false;
        } catch (Exception e) {
            // Log the error
            return false;
        }
    }
}
