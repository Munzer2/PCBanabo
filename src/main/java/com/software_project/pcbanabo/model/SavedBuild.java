package com.software_project.pcbanabo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "saved_builds")
public class SavedBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "cpu_id")
    private Integer cpuId;

    @Column(name = "motherboard_id")
    private Integer motherboardId;

    @Column(name = "ram_id")
    private Integer ramId;

    @Column(name = "ssd_id")
    private Integer ssdId;

    @Column(name = "gpu_id")
    private Integer gpuId;

    @Column(name = "psu_id")
    private Integer psuId;

    @Column(name = "casing_id")
    private Integer casingId;

    @Column(name = "cpu_cooler_id")
    private Integer cpuCoolerId;

    @Column(name = "build_name")
    private String buildName;

    @Column(name = "saved_at")
    private LocalDateTime savedAt;

    @Column(name = "public")
    private Boolean isPublic; // `public` is a reserved keyword, use `isPublic` instead

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCpuId() {
        return cpuId;
    }

    public void setCpuId(Integer cpuId) {
        this.cpuId = cpuId;
    }

    public Integer getMotherboardId() {
        return motherboardId;
    }

    public void setMotherboardId(Integer motherboardId) {
        this.motherboardId = motherboardId;
    }

    public Integer getRamId() {
        return ramId;
    }

    public void setRamId(Integer ramId) {
        this.ramId = ramId;
    }

    public Integer getSsdId() {
        return ssdId;
    }

    public void setSsdId(Integer ssdId) {
        this.ssdId = ssdId;
    }

    public Integer getGpuId() {
        return gpuId;
    }

    public void setGpuId(Integer gpuId) {
        this.gpuId = gpuId;
    }

    public Integer getPsuId() {
        return psuId;
    }

    public void setPsuId(Integer psuId) {
        this.psuId = psuId;
    }

    public Integer getCasingId() {
        return casingId;
    }

    public void setCasingId(Integer casingId) {
        this.casingId = casingId;
    }

    public Integer getCpuCoolerId() {
        return cpuCoolerId;
    }

    public void setCpuCoolerId(Integer cpuCoolerId) {
        this.cpuCoolerId = cpuCoolerId;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
