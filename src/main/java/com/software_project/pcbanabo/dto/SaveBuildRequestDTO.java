package com.software_project.pcbanabo.dto;

public class SaveBuildRequestDTO {
    private Integer cpuId;
    private Integer motherboardId;
    private Integer ramId;
    private Integer ssdId;
    private Integer gpuId;
    private Integer psuId;
    private Integer casingId;
    private Integer cpuCoolerId;

    private String buildName;
    private Boolean isPublic;

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

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
