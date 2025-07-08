package com.software_project.pcbanabo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "casing")
public class Casing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand_name;
    @Column(name = "model_name", nullable = false)
    private String modelName;
    @Column(name = "motherboard_support")
    private String motherboardSupport;
    @Column(name = "psu_clearance")
    private int psuClearance;
    @Column(name = "gpu_clearance")
    private int gpuClearance;
    @Column(name = "cpu_clearance")
    private int cpuClearance;
    private String top_rad_support;
    private String bottom_rad_support;
    private String side_rad_support;
    private int included_fan;
    private boolean rgb;
    private boolean display;
    private String color;
    private double avg_price;
    private String official_product_url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getModel_name() {
        return modelName;
    }

    public void setModel_name(String model_name) {
        this.modelName = model_name;
    }

    public String getMotherboardSupport() {
        return motherboardSupport;
    }

    public void setMotherboardSupport(String motherboard_support) {
        this.motherboardSupport = motherboard_support;
    }

    public int getPsuClearance() {
        return psuClearance;
    }

    public void setPsuClearance(int psu_clearance) {
        this.psuClearance = psu_clearance;
    }

    public int getGpuClearance() {
        return gpuClearance;
    }

    public void setGpuClearance(int gpu_clearance) {
        this.gpuClearance = gpu_clearance;
    }

    public int getCpuClearance() {
        return cpuClearance;
    }

    public void setCpuClearance(int cpu_clearance) {
        this.cpuClearance = cpu_clearance;
    }

    public String getTop_rad_support() {
        return top_rad_support;
    }

    public void setTop_rad_support(String top_rad_support) {
        this.top_rad_support = top_rad_support;
    }

    public String getBottom_rad_support() {
        return bottom_rad_support;
    }

    public void setBottom_rad_support(String bottom_rad_support) {
        this.bottom_rad_support = bottom_rad_support;
    }

    public String getSide_rad_support() {
        return side_rad_support;
    }

    public void setSide_rad_support(String side_rad_support) {
        this.side_rad_support = side_rad_support;
    }

    public int getIncluded_fan() {
        return included_fan;
    }

    public void setIncluded_fan(int included_fan) {
        this.included_fan = included_fan;
    }

    public boolean isRgb() {
        return rgb;
    }

    public void setRgb(boolean rgb) {
        this.rgb = rgb;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(double avg_price) {
        this.avg_price = avg_price;
    }

    public String getOfficial_product_url() {
        return official_product_url;
    }

    public void setOfficial_product_url(String official_product_url) {
        this.official_product_url = official_product_url;
    }
}
