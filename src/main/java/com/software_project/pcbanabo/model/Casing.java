package com.software_project.pcbanabo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "casing")
public class Casing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand_name;
    private String model_name;
    private String motherboard_support;
    private int psu_clearance;
    private int gpu_clearance;
    private int cpu_clearance;
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
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getMotherboard_support() {
        return motherboard_support;
    }

    public void setMotherboard_support(String motherboard_support) {
        this.motherboard_support = motherboard_support;
    }

    public int getPsu_clearance() {
        return psu_clearance;
    }

    public void setPsu_clearance(int psu_clearance) {
        this.psu_clearance = psu_clearance;
    }

    public int getGpu_clearance() {
        return gpu_clearance;
    }

    public void setGpu_clearance(int gpu_clearance) {
        this.gpu_clearance = gpu_clearance;
    }

    public int getCpu_clearance() {
        return cpu_clearance;
    }

    public void setCpu_clearance(int cpu_clearance) {
        this.cpu_clearance = cpu_clearance;
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
