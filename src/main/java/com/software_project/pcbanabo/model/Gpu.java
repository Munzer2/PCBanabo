package com.software_project.pcbanabo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gpu")
public class Gpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand_name;
    private String model_name;
    private String gpu_core;
    private int vram;
    private int tdp;
    private String power_connector_type;
    private int power_connector_count;
    private int card_length;
    private int card_height;
    private int card_thickness;
    private Boolean rgb;
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

    public String getGpu_core() {
        return gpu_core;
    }

    public void setGpu_core(String gpu_core) {
        this.gpu_core = gpu_core;
    }

    public int getVram() {
        return vram;
    }

    public void setVram(int vram) {
        this.vram = vram;
    }

    public int getTdp() {
        return tdp;
    }

    public void setTdp(int tdp) {
        this.tdp = tdp;
    }

    public String getPower_connector_type() {
        return power_connector_type;
    }

    public void setPower_connector_type(String power_connector_type) {
        this.power_connector_type = power_connector_type;
    }

    public int getPower_connector_count() {
        return power_connector_count;
    }

    public void setPower_connector_count(int power_connector_count) {
        this.power_connector_count = power_connector_count;
    }

    public int getCard_length() {
        return card_length;
    }

    public void setCard_length(int card_length) {
        this.card_length = card_length;
    }

    public int getCard_height() {
        return card_height;
    }

    public void setCard_height(int card_height) {
        this.card_height = card_height;
    }

    public int getCard_thickness() {
        return card_thickness;
    }

    public void setCard_thickness(int card_thickness) {
        this.card_thickness = card_thickness;
    }

    public Boolean getRgb() {
        return rgb;
    }

    public void setRgb(Boolean rgb) {
        this.rgb = rgb;
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
