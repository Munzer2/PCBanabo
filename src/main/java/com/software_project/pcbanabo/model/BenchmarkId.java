package com.software_project.pcbanabo.model;

import java.io.Serializable;
import java.util.Objects;

public class BenchmarkId implements Serializable {
    private Long cpuId;
    private Long gpuId;

    public BenchmarkId() {}

    public BenchmarkId(Long cpuId, Long gpuId) {
        this.cpuId = cpuId;
        this.gpuId = gpuId;
    }

    public Long getCpuId() {
        return cpuId;
    }

    public void setCpuId(Long cpuId) {
        this.cpuId = cpuId;
    }

    public Long getGpuId() {
        return gpuId;
    }

    public void setGpuId(Long gpuId) {
        this.gpuId = gpuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenchmarkId that = (BenchmarkId) o;
        return Objects.equals(cpuId, that.cpuId) && Objects.equals(gpuId, that.gpuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpuId, gpuId);
    }
}
