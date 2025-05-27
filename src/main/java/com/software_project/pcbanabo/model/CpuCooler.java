package com.software_project.pcbanabo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cpu_cooler")
public class CpuCooler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand_name;
    private String model_name;
    private String socket_support;
    private String cooler_type;
    @Column(name = "tower_height")
    private Integer towerHeight;
    private Integer radiator_size;
    @Column(name = "cooling_capacity")
    private Integer coolingCapacity;
    private Integer ram_clearance;
    private String color;
    private Boolean rgb;
    private Boolean display;
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

    public String getSocket_support() {
        return socket_support;
    }

    public void setSocket_support(String socket_support) {
        this.socket_support = socket_support;
    }

    public String getCooler_type() {
        return cooler_type;
    }

    public void setCooler_type(String cooler_type) {
        this.cooler_type = cooler_type;
    }

    public Integer getTowerHeight() {
        return towerHeight;
    }

    public void setTowerHeight(Integer tower_height) {
        this.towerHeight = tower_height;
    }

    public Integer getRadiator_size() {
        return radiator_size;
    }

    public void setRadiator_size(Integer radiator_size) {
        this.radiator_size = radiator_size;
    }

    public Integer getCoolingCapacity() {
        return coolingCapacity;
    }

    public void setCoolingCapacity(Integer cooling_capacity) {
        this.coolingCapacity = cooling_capacity;
    }

    public Integer getRam_clearance() {
        return ram_clearance;
    }

    public void setRam_clearance(Integer ram_clearance) {
        this.ram_clearance = ram_clearance;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean isRgb() {
        return rgb;
    }

    public void setRgb(Boolean rgb) {
        this.rgb = rgb;
    }

    public Boolean isDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
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
