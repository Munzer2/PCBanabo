package com.software_project.pcbanabo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "benchmark")
@IdClass(BenchmarkId.class)
public class Benchmark {
    @Id
    @Column(name = "cpu_id")
    private Long cpuId;
    @Id
    @Column(name = "gpu_id")
    private Long gpuId;
    @Column(name = "cinebench_single")
    private Integer cinebenchSingle;
    @Column(name = "cinebench_multi")
    private Integer cinebenchMulti;
    private Integer blender;
    private Integer geekbench;
    private Integer photoshop;
    @Column(name = "premiere_pro")
    private Integer premierePro;
    private Integer lightroom;
    private Integer davinci;
    @Column(name = "horizon_zero_dawn")
    private Integer horizonZeroDawn;
    @Column(name = "f1_2024")
    private Integer f12024;
    private Integer valorant;
    private Integer overwatch;
    private Integer csgo;
    private Integer fc2025;
    @Column(name = "black_myth_wukong")
    private Integer blackMythWukong;

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

    public Integer getCinebenchSingle() {
        return cinebenchSingle;
    }

    public void setCinebenchSingle(Integer cinebenchSingle) {
        this.cinebenchSingle = cinebenchSingle;
    }

    public Integer getCinebenchMulti() {
        return cinebenchMulti;
    }

    public void setCinebenchMulti(Integer cinebenchMulti) {
        this.cinebenchMulti = cinebenchMulti;
    }

    public Integer getBlender() {
        return blender;
    }

    public void setBlender(Integer blender) {
        this.blender = blender;
    }

    public Integer getGeekbench() {
        return geekbench;
    }

    public void setGeekbench(Integer geekbench) {
        this.geekbench = geekbench;
    }

    public Integer getPhotoshop() {
        return photoshop;
    }

    public void setPhotoshop(Integer photoshop) {
        this.photoshop = photoshop;
    }

    public Integer getPremierePro() {
        return premierePro;
    }

    public void setPremierePro(Integer premierePro) {
        this.premierePro = premierePro;
    }

    public Integer getLightroom() {
        return lightroom;
    }

    public void setLightroom(Integer lightroom) {
        this.lightroom = lightroom;
    }

    public Integer getDavinci() {
        return davinci;
    }

    public void setDavinci(Integer davinci) {
        this.davinci = davinci;
    }

    public Integer getHorizonZeroDawn() {
        return horizonZeroDawn;
    }

    public void setHorizonZeroDawn(Integer horizonZeroDawn) {
        this.horizonZeroDawn = horizonZeroDawn;
    }

    public Integer getF12024() {
        return f12024;
    }

    public void setF12024(Integer f12024) {
        this.f12024 = f12024;
    }

    public Integer getValorant() {
        return valorant;
    }

    public void setValorant(Integer valorant) {
        this.valorant = valorant;
    }

    public Integer getOverwatch() {
        return overwatch;
    }

    public void setOverwatch(Integer overwatch) {
        this.overwatch = overwatch;
    }

    public Integer getCsgo() {
        return csgo;
    }

    public void setCsgo(Integer csgo) {
        this.csgo = csgo;
    }

    public Integer getFc2025() {
        return fc2025;
    }

    public void setFc2025(Integer fc2025) {
        this.fc2025 = fc2025;
    }

    public Integer getBlackMythWukong() {
        return blackMythWukong;
    }

    public void setBlackMythWukong(Integer blackMythWukong) {
        this.blackMythWukong = blackMythWukong;
    }
}
